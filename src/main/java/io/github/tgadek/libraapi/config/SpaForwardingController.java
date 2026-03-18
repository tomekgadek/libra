package io.github.tgadek.libraapi.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaForwardingController implements ErrorController {

    /**
     * Forwards all unmatched routes to index.html so that
     * React Router can handle client-side routing.
     * 
     * API endpoints return JSON responses directly, so they never
     * reach this error controller. Only browser navigation to
     * React routes (like /books, /books/{id}) will hit this
     * when there's no static file to serve.
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "forward:/index.html";
    }
}
