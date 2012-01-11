/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;


import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("feb")
@RequestMapping("/")
public final class FEBController {

    private List<Member> members = new ArrayList<Member>();

    public FEBController() {
        members.add(new Member("John", "Lennon"));
        members.add(new Member("Paul", "McCartney"));
        members.add(new Member("George", "Harrison"));
        members.add(new Member("Ringo", "Starr"));
    }

    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute(members);
        return "index";
    }
    
    @RequestMapping("{viewName}")
    public String fallback(@PathVariable String viewName) {
        System.out.println("Request for " + viewName);
        return viewName;
    }

    @RequestMapping("member")
    public void member(@RequestParam("id") Integer id, Model model) {
        model.addAttribute(members.get(id));
    }
}

