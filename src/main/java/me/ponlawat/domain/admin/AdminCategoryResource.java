package me.ponlawat.domain.admin;

import me.ponlawat.domain.category.Category;
import me.ponlawat.domain.category.CategoryService;
import me.ponlawat.domain.category.dto.CategoryEditRequest;
import me.ponlawat.infrastructure.auth.AdminRequired;
import me.ponlawat.infrastructure.auth.JwtRequired;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/admin/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@JwtRequired
@AdminRequired
public class AdminCategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    @Path("")
    public List<Category> listCategory() {
        return categoryService.listCategory();
    }

    @GET
    @Path("/{id}")
    public Category detailCategory(@PathParam("id") long id) {
        return categoryService.getCategory(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") long id) {
        categoryService.removeCategory(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/{id}")
    public Category editCategory(@PathParam("id") long id, @Valid CategoryEditRequest categoryEditRequest) {
        return categoryService.updateCategory(id, categoryEditRequest);
    }
}
