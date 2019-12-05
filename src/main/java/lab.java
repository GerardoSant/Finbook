import static spark.Spark.get;
import static spark.Spark.staticFiles;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

public class lab {
    public static void main(final String[] args) {

        staticFiles.location("/public");


        get("/hello", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("hello", "Velocity World");
            model.put("person", new Person("Foobar"));

            // The vm files are located under the resources directory
            return new ModelAndView(model, "hello.vm");
        }, new VelocityTemplateEngine());

    }

    public static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
