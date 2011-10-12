/*
 * (c) Copyright 2011 Marcus Thiesen (marcus@thiesen.org)
 *
 *  This file is part of Bahn Client.
 *
 *  Bahn Client is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Bahn Client is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with gitant.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.thiesen.hafas.request;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.thiesen.hafas.Configuration;
import org.thiesen.hafas.request.parts.Language;
import org.thiesen.hafas.request.parts.Product;
import org.thiesen.hafas.request.parts.Version;
import org.thiesen.hafas.response.RawResponse;
import org.thiesen.hafas.response.Response;


public abstract class BaseRequest implements Request {

    protected static Element makeRootElement( final Version version, final Product product, final Language language ) {
        final Element rootElement = new Element( "ReqC" );

        rootElement.setAttribute( version.makeAttribute() );
        rootElement.setAttribute( product.makeAttribute() );
        rootElement.setAttribute( language.makeAttribute() );

        return rootElement;
    }

    @Override
    public String makeStringRepresentation( final Configuration config ) {
        final Element rootElement = makeRootElement( Version.getDefault() ,config.getProduct(),  Language.getDefault() );

        rootElement.addContent( makeElement() );

        return makeStringFrom( rootElement, config.getEncoding() );
    }

    private String makeStringFrom( final Element rootElement, final Charset charset ) {
        final Document doc = new Document( rootElement );

        final Writer outputWriter = new StringWriter();

        final Format format = Format.getPrettyFormat();
        format.setExpandEmptyElements( true );
        format.setEncoding( charset.name() );
        final XMLOutputter fmt = new XMLOutputter( format );

        try {
            fmt.output( doc, outputWriter );
            outputWriter.flush();
            outputWriter.close();

            return outputWriter.toString();
        } catch ( final IOException e ) {
            throw new Error("IOException on in memory streams!", e );
        }

    }

    protected abstract Collection<Element> makeElement();

    @Override
    public Response makeResponseFrom( final String extractedResponseContent ) {
        return RawResponse.from( extractedResponseContent );
    }

    
    
}
