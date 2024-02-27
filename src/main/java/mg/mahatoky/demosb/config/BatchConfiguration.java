package mg.mahatoky.demosb.config;

import mg.mahatoky.demosb.model.dto.BannedUser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.File;

/**
 * Look if banned-user-list.csv is present, then read it and save into DB
 * @author mtk_ext
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Value("${banned.filename}")
    private String bannedFilename;



    @Bean
    public FlatFileItemReader<BannedUser> bannedUserFlatFileItemReader(){
        FlatFileItemReader<BannedUser> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(bannedFilename));
        reader.setLineMapper(new DefaultLineMapper<BannedUser>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("firstName", "lastName");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<BannedUser>() {{
                setTargetType(BannedUser.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<BannedUser, BannedUser> itemProcessor() {
        return bannedUser -> bannedUser;
    }

    @Bean
    public JdbcBatchItemWriter<BannedUser> bannedUserJdbcBatchItemWriter() {
        JdbcBatchItemWriter<BannedUser> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO banned_users (first_name, last_name) VALUES (:firstname, :lastname)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<BannedUser, BannedUser>chunk(10)
                .reader(bannedUserFlatFileItemReader())
                .processor(itemProcessor())
                .writer(bannedUserJdbcBatchItemWriter())
                .build();
    }

    @Bean
    public Step deleteCsvStep(){
        return stepBuilderFactory.get("deleteFileStep")
                .tasklet((contribution, chunkContext) -> {
                    File file = new ClassPathResource(bannedFilename).getFile();
                    if (file.delete()) {
                        System.out.println("File deleted successfully.");
                    } else {
                        System.out.println("Failed to delete the file.");
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Job saveBannerUsersJob() {
        return jobBuilderFactory.get("saveBannerUsersJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

}
