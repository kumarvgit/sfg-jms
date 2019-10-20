package guru.springframework.sfgjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HelloWorldMessage implements Serializable {
    static final long serialVersionUID = 3449397820528549932L;
    private UUID id;
    private String message;
}
