package bunny.backend.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {
    @GetMapping("/swagger-ui/index.html")
    public String swaggerUi() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Swagger UI</title>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"http://15.164.204.26/swagger-ui/swagger-ui.css\" />\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"http://15.164.204.26/swagger-ui/index.css\" />\n" +
                "    <link rel=\"icon\" type=\"image/png\" href=\"http://15.164.204.26/swagger-ui/favicon-32x32.png\" sizes=\"32x32\" />\n" +
                "    <link rel=\"icon\" type=\"image/png\" href=\"http://15.164.204.26/swagger-ui/favicon-16x16.png\" sizes=\"16x16\" />\n" +
                "  </head>\n" +
                "\n" +
                "  <body>\n" +
                "    <div id=\"swagger-ui\"></div>\n" +
                "    <script src=\"http://15.164.204.26/swagger-ui/swagger-ui-bundle.js\" charset=\"UTF-8\"> </script>\n" +
                "    <script src=\"http://15.164.204.26/swagger-ui/swagger-ui-standalone-preset.js\" charset=\"UTF-8\"> </script>\n" +
                "    <script src=\"http://15.164.204.26/swagger-ui/swagger-initializer.js\" charset=\"UTF-8\"> </script>\n" +
                "  </body>\n" +
                "</html>\n";
    }
}
