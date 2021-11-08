package com.uala.services.handler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.uala.services.model.Contact;
import com.uala.services.model.Response;
import com.uala.services.repository.Repository;


public class ContactHandler implements RequestHandler<Contact, Response> {
    private Repository repository;

    public ContactHandler() {
        this(new Repository());
    }

    public ContactHandler(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Response handleRequest(Contact contact, Context context) {
        Response response = new Response();
        repository.createContact(contact);
        response.setMessage("CREATED NEW CONTACT");
        response.setStatus("201");
        return response;
    }
}
