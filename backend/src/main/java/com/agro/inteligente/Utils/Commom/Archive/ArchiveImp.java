package com.agro.inteligente.Utils.Commom.Archive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArchiveImp implements IArchive{

    private final ClassLoaderTemplateResolver classLoaderTemplateResolver;

    @Override
    public String alterArchive(String name, Map<String, Object> parameters) {

        this.classLoaderTemplateResolver.setPrefix("/templates/");
        this.classLoaderTemplateResolver.setSuffix(".html");

        this.classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);

        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(this.classLoaderTemplateResolver);

        Context context = new Context();

        context.setVariables(parameters);

        return templateEngine.process(name, context);
    }
}
