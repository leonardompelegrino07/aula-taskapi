package br.edu.ifsp.web.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {
	void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;

}
