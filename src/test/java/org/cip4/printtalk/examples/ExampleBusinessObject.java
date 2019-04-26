/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2019 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.Confirmation;
import org.cip4.printtalk.Price.EnumPriceType;
import org.cip4.printtalk.Price.EnumTaxType;
import org.cip4.printtalk.PrintTalk;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;
import org.cip4.printtalk.PrintTalkTestCase;
import org.cip4.printtalk.PurchaseOrder;
import org.cip4.printtalk.Quotation;
import org.cip4.printtalk.Quote;
import org.cip4.printtalk.RFQ;
import org.cip4.printtalk.Refusal;
import org.cip4.printtalk.Refusal.EnumReason;
import org.cip4.printtalk.builder.PrintTalkBuilderFactory;
import org.junit.Test;

public class ExampleBusinessObject extends PrintTalkTestCase
{

	/**
	 *
	 */
	@Test
	public synchronized void testCart()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();
		final PurchaseOrder po = (PurchaseOrder) pt.appendRequest(EnumBusinessObject.PurchaseOrder, null);
		po.setBusinessID("cart1");
		po.appendXJDF(new XJDFHelper("cart1.item1", null, null));
		po.appendXJDF(new XJDFHelper("cart1.item2", null, null));
		pt.cleanUp();
		setSnippet(po.getRoot(), true);
		writeExample(pt, "businessobjects/PurchaseOrderCart.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testQuotation()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();

		final Quotation quotation = (Quotation) pt.appendRequest(EnumBusinessObject.Quotation, null);
		quotation.setBusinessID("q1");
		quotation.setEstimate(false);
		quotation.setExpires(new JDFDate().setTime(18, 0, 0).addOffset(0, 0, 0, 14));
		final Quote q = quotation.appendQuote();
		q.setQuoteID("q1");
		q.getCreatePricing().addPrice(EnumPriceType.Total, EnumTaxType.Net, "100 business cards", 420000.00);
		q.getCreatePricing().setCurrency("GBP");
		final Quote q2 = quotation.appendQuote();
		q2.setQuoteID("q1");
		q2.getCreatePricing().addPrice(EnumPriceType.Total, EnumTaxType.Net, "100 business cards", 42.00);
		q2.getCreatePricing().setCurrency("DKK");
		pt.cleanUp();
		setSnippet(quotation, true);
		writeExample(pt, "businessobjects/Quotation.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testIdRfq()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();
		final RFQ rfq = (RFQ) pt.appendRequest(EnumBusinessObject.RFQ, null);
		rfq.setBusinessID("RFQ_1");
		rfq.setExpiresDays(5);
		rfq.appendXJDF(new XJDFHelper("MyJob", null));
		pt.cleanUp();
		setSnippet(rfq.getRequest(), true);
		writeExample(pt, "idusage/SimpleRFQ.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testIdChangeRfq()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();
		final RFQ rfq = (RFQ) pt.appendRequest(EnumBusinessObject.RFQ, null);
		rfq.setBusinessID("Change_RFQ_1");
		rfq.setBusinessRefID("PO_1");
		rfq.setExpiresDays(1);
		rfq.appendXJDF(new XJDFHelper("MyJob", null));
		rfq.appendXJDFElement(ElementName.COMMENT).setText("Please call me asap!");
		pt.cleanUp();
		setSnippet(rfq.getRequest(), true);
		writeExample(pt, "idusage/ChangeRFQ.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testIdQuotation()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();

		final Quotation quotation = (Quotation) pt.appendRequest(EnumBusinessObject.Quotation, null);
		quotation.setBusinessRefID("RFQ_1");
		quotation.setBusinessID("Quotation_1");
		quotation.setExpiresDays(22);
		final Quote q1 = quotation.appendQuote();
		q1.setQuoteID("Quote_1");
		q1.getCreatePricing().setCurrency("CHF");
		q1.getCreatePricing().addPrice(EnumPriceType.Product, EnumTaxType.Gross, "all of 1", 333.22);
		final Quote q2 = quotation.appendQuote();
		q2.setQuoteID("Quote_2");
		q2.getCreatePricing().setCurrency("CHF");
		q2.getCreatePricing().addPrice(EnumPriceType.Product, EnumTaxType.Gross, "all of 2", 222.22);
		pt.cleanUp();
		setSnippet(quotation.getRequest(), true);
		writeExample(pt, "idusage/SimpleQuotation.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testIdConfirmation()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();

		final Confirmation c = (Confirmation) pt.appendRequest(EnumBusinessObject.Confirmation, null);
		c.setBusinessRefID("PO_1");
		c.setBusinessID("Confirmation_1");
		pt.cleanUp();
		setSnippet(c.getRequest(), true);
		writeExample(pt, "idusage/SimpleConfirmation.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testIdRefusal()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();

		final Refusal ref = (Refusal) pt.appendRequest(EnumBusinessObject.Refusal, null);
		ref.setBusinessRefID("PO_1");
		ref.setBusinessID("Confirmation_1");
		ref.setAttribute(AttributeName.REASON, EnumReason.Busy.name());
		ref.setAttribute(AttributeName.REASONDETAILS, "Christmas");
		pt.cleanUp();
		setSnippet(ref.getRequest(), true);
		writeExample(pt, "idusage/SimpleRefusal.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testIdPurchaseOrder()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();

		final PurchaseOrder po = (PurchaseOrder) pt.appendRequest(EnumBusinessObject.PurchaseOrder, null);
		po.setBusinessRefID("Quotation_1");
		po.setBusinessID("PO_1");
		po.setQuoteID("Quote_1");
		po.appendXJDF(new XJDFHelper("MyJob", null));
		setSnippet(po.getRequest(), true);
		pt.cleanUp();
		writeExample(pt, "idusage/SimplePO.ptk");
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	public void setUp() throws Exception
	{
		KElement.setLongID(false);
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		theFactory.resetInstance();
		theFactory.setFromURL("https://customer.com");
		theFactory.setToURL("https://printer.com");

		super.setUp();
	}

}
