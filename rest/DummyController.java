package ru.greenatom.atomskils.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.greenatom.atomskils.model.Dummys;
import ru.greenatom.atomskils.model.User;
import ru.greenatom.atomskils.security.Auth;
import ru.greenatom.atomskils.services.DummysService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/dummys")
public class DummyController {


    @Autowired
    private DummysService dummysService;

    /**
     * Запрашиваем список dummys
     *
     * @return
     */
    @GetMapping()
    @ResponseBody
    @RolesAllowed({"LIST_DUMMY"})
    public List<Dummys> getAll(Principal principal) {
        return dummysService.getAll((User) ((Auth) principal).getPrincipal());
    }

    @GetMapping("/{id}")
    @ResponseBody
    @RolesAllowed({"DUMMY_BY_ID"})
    public Dummys getById(@PathVariable Integer id) {
        return dummysService.getById(id);
    }

    @PostMapping()
    @RolesAllowed({"CREATE_DUMMY"})
    public Dummys create(@RequestBody Dummys dummy, Principal principal) {
     return dummysService.create(dummy, (User) ((Auth) principal).getPrincipal());
    }

    @PutMapping()
    @RolesAllowed({"EDIT_DUMMY"})
    public Dummys edit(@RequestBody Dummys dummy) {
        return dummysService.edit(dummy);
    }

    @DeleteMapping()
    @RolesAllowed({"DELETE_DUMMY"})
    public void delete(@RequestBody Dummys dummy) {
        dummysService.delete(dummy);
    }

}
