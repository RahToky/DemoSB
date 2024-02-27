package mg.mahatoky.demosb.model.dto.response;

import lombok.Data;

/**
 * @author mtk_ext
 */
@Data
public class ErrorResponse extends AbstractResponse{
    private String message;

    public ErrorResponse(String message){
        super(500);
        this.message = message;
    }

    public ErrorResponse(int status, String message) {
        super(status);
        this.message = message;
    }
}
