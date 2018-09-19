package pl.coderslab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import pl.coderslab.model.User;

@SpringBootApplication
@ComponentScan
public class Warjees09Application {

    public static void main(String[] args) {
        SpringApplication.run(Warjees09Application.class, args);
    }


}
