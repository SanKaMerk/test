package ru.greenatom.atomskils.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.greenatom.atomskils.model.Roles;
import ru.greenatom.atomskils.model.User;
import ru.greenatom.atomskils.resources.RolesRepository;
import ru.greenatom.atomskils.security.Auth;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {


    @Autowired
    private RolesRepository rolesRepository;

    /**
     * Запрашиваем список dummys
     *
     * @return
     */
    @GetMapping()
    @ResponseBody
    @RolesAllowed({"ROLES_LIST"})
    public List<Roles> getAll(Principal principal) {
        return rolesRepository.getAll((User) ((Auth) principal).getPrincipal());
    }

    @GetMapping("/{id}")
    @ResponseBody
    @RolesAllowed({"ROLES_BY_ID"})
    public Roles getById(@PathVariable Integer id) {
        return rolesRepository.getById(id);
    }

    @PostMapping()
    @RolesAllowed({"ROLES_CREATE"})
    public Roles create(@RequestBody Roles roles, Principal principal) {
        roles.setId(rolesRepository.create(roles, (User) ((Auth) principal).getPrincipal()));
        return roles;
    }

    @PutMapping()
    @RolesAllowed({"ROLES_EDIT"})
    public Roles edit(@RequestBody Roles roles) {
        return rolesRepository.edit(roles);
    }

    @DeleteMapping()
    @RolesAllowed({"ROLES_DELETE"})
    public void delete(@RequestBody Roles roles) {
        rolesRepository.delete(roles);
    }

}
