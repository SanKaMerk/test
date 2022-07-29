package ru.greenatom.atomskils.rest;

import org.springframework.web.bind.annotation.*;
import ru.greenatom.atomskils.commons.FuncRoles;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/funcroles")
public class FuncRolesController {

    @GetMapping()
    @PermitAll
    public @ResponseBody FuncRoles[] getFuncRoles(HttpServletRequest request, HttpServletResponse response) {
        return FuncRoles.values();
    }
}
