package com.somnus.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.somnus.domain.Person;
import com.somnus.domain.Product;

@Path("/person")
public class PersonResource {

	@GET
	@Path("getPersonInfo")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Person getPersonInfo() {
		Person person = new Person("张三", 23);
		List<Product> productList = new ArrayList<Product>();
		productList.add(new Product("产品1", "CP00001"));
		productList.add(new Product("产品2", "CP00002"));
		person.setProducts(productList);
		return person;
		//return Response.status(200).entity(person).build();
		//return new Product("产品1", "CP00001");
	}
}
