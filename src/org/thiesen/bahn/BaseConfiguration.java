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
package org.thiesen.bahn;

import java.nio.charset.Charset;

import org.thiesen.hafas.Configuration;
import org.thiesen.hafas.request.parts.Product;

import com.google.common.base.Charsets;

public abstract class BaseConfiguration implements Configuration {


    @Override
    public Product getProduct() {
        return Product.forName("Test");
    }

    @Override
    public Charset getEncoding() {
        return Charsets.ISO_8859_1;
    }

    

}

