package server.resource;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;

import com.codahale.metrics.annotation.Timed;
import server.ListItem;

@Path("/main-list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListResource {
  private JacksonDBCollection<ListItem, String> collection;

  public ListResource( JacksonDBCollection<ListItem, String> mainList ) {
    this.collection = mainList;
  }

  @POST
  @Timed
  public Response publishNewBlog( @Valid ListItem newItem ) {
    collection.insert( newItem );
    return Response.noContent().build();
  }
}
