/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2020 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This product includes software developed by the The International Cooperation for
 * the Integration of Processes in Prepress, Press and Postpress (www.cip4.org)" Alternately, this acknowledgment may appear in the software itself, if and wherever such third-party acknowledgments
 * normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of Processes in Prepress, Press and Postpress" must not be used to endorse or promote products derived from this software
 * without prior written permission. For written permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4", nor may "CIP4" appear in their name, without prior written permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE. ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the The International Cooperation for the Integration of Processes in Prepress, Press and Postpress and was
 * originally based on software copyright (c) 1999-2001, Heidelberger Druckmaschinen AG copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the Integration of Processes in Prepress, Press and Postpress , please see <http://www.cip4.org/>.
 *
 *
 */
package org.cip4.printtalk.examples;

import org.cip4.jdflib.core.KElement;
import org.cip4.printtalk.Credential;
import org.cip4.printtalk.HeaderBase.EnumHeaderType;
import org.cip4.printtalk.Invoice;
import org.cip4.printtalk.Price;
import org.cip4.printtalk.Price.EnumPriceType;
import org.cip4.printtalk.Price.EnumTaxType;
import org.cip4.printtalk.Pricing;
import org.cip4.printtalk.PrintTalk;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;
import org.cip4.printtalk.PrintTalkTestCase;
import org.cip4.printtalk.builder.PrintTalkBuilderFactory;
import org.junit.jupiter.api.Test;

public class ExampleElement extends PrintTalkTestCase
{

	/**
	 *
	 */
	@Test
	public synchronized void testPricing()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();
		pt.setCredential(EnumHeaderType.From, Credential.DOMAIN_URL, "https://customer.com");
		pt.setCredential(EnumHeaderType.To, Credential.DOMAIN_URL, "https://printer.com");
		final Invoice invoice = (Invoice) pt.appendRequest(EnumBusinessObject.Invoice, null);
		invoice.setBusinessID("invoice1");
		invoice.setDueDate(33);
		final Pricing p = invoice.getCreatePricing();
		p.setCurrency("CAD");
		final Price pi1 = p.addPrice(EnumPriceType.Product, EnumTaxType.Gross, "100 expensive item #1", 42.00);
		pi1.setAmount(100);
		pi1.addItemRef("product1");
		final Price pi2 = p.addPrice(EnumPriceType.Product, EnumTaxType.Gross, "200 cheap item #2", 21.00);
		pi2.addItemRef("product2");
		pi1.setAmount(200);
		final Price ptot = p.addPrice(EnumPriceType.Total, EnumTaxType.Gross, "Total", 63.00);
		ptot.refPrice(pi1);
		ptot.refPrice(pi2);
		final Price ptx = p.addPrice(EnumPriceType.Total, EnumTaxType.Tax, "Included 20% tax", 63.00 - 63.00 / 1.2);
		pt.cleanUp();
		setSnippet(p.getRoot(), true);
		ptx.refPrice(pi1);
		ptx.refPrice(pi2);
		writeExample(pt, "subelements/Pricing.ptk");
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	public void setUp() throws Exception
	{
		KElement.setLongID(false);
		PrintTalkBuilderFactory.getTheFactory().resetInstance();
		super.setUp();
	}

}
