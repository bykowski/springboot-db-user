package pl.bykowski.springbootdbuser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRest {

    @GetMapping("/test1")
    public String getTest1() {
        return "dla zalogowanego";
    }

    @GetMapping("/test2")
    public String getTest2() {
        return "dla wszystkich";
    }

    @GetMapping("/test3")
    public String getTest3() {
        return "dla użytkownika";
    }

    @GetMapping("/test4")
    public String getTest4() {
        return "dla admina";
    }

    @GetMapping("/test5")
    public String getTest5() {
        return "dla admina i usera";
    }


}
