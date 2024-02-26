package mg.mahatoky.demosb.controller;

import mg.mahatoky.demosb.model.dto.AbstractResponse;
import mg.mahatoky.demosb.model.dto.ErrorResponse;
import mg.mahatoky.demosb.model.dto.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mtk_ext
 */
@RestController
public class HomeController {

    @GetMapping("/success")
    public ResponseEntity<AbstractResponse> success(){
        return ResponseEntity.ok(new SuccessResponse<String>("success"));
    }
    @GetMapping("/failed")
    public ResponseEntity<AbstractResponse> failed(){
        return ResponseEntity.ok(new ErrorResponse("failed"));
    }


}
