package mg.mahatoky.demosb.model.dto;

import lombok.Data;

/**
 * @author mtk_ext
 */
@Data
public class SuccessResponse<T> extends AbstractResponse{

    private T data;

    public SuccessResponse(T data){
        super(200);
        this.data = data;
    }

    public SuccessResponse(int status, T data) {
        super(status);
        this.data = data;
    }
}
