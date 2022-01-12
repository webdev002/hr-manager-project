package uz.pdp.hrmanagerjwtsecurityrealproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    public String message;

    public boolean success;

    private Object object;

    public ApiResponse(String message,boolean success) {
        this.message = message;
        this.success = success;
    }

}
