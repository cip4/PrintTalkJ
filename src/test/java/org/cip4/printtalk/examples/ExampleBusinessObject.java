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

import static org.junit.Assert.assertEquals;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.datatypes.JDFIntegerList;
import org.cip4.jdflib.extensions.AuditHelper;
import org.cip4.jdflib.extensions.AuditPoolHelper;
import org.cip4.jdflib.extensions.MessageResourceHelper;
import org.cip4.jdflib.extensions.ProductHelper;
import org.cip4.jdflib.extensions.ResourceHelper;
import org.cip4.jdflib.extensions.SetHelper;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.resource.process.JDFDeliveryParams;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.AbstractPrintTalk;
import org.cip4.printtalk.BusinessObject.EnumResult;
import org.cip4.printtalk.BusinessObject.EnumUpdateMethod;
import org.cip4.printtalk.Confirmation;
import org.cip4.printtalk.ContentDelivery;
import org.cip4.printtalk.ContentDeliveryResponse;
import org.cip4.printtalk.Invoice;
import org.cip4.printtalk.OrderStatusResponse;
import org.cip4.printtalk.Price;
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
		po.setExpires(new JDFDate());
		final XJDFHelper xjdf1 = new XJDFHelper("cart1.item1", null, null);
		xjdf1.removeSet(ElementName.NODEINFO);
		xjdf1.getRoot().removeChild(ElementName.AUDITPOOL, null, 0);
		final ProductHelper bc = xjdf1.appendProduct();
		bc.setDescriptiveName("BusinessCards");
		bc.setRoot();
		bc.setAmount(100);
		po.appendXJDF(xjdf1);

		final XJDFHelper xjdf2 = new XJDFHelper("cart1.item2", null, null);
		xjdf2.removeSet(ElementName.NODEINFO);
		xjdf2.getRoot().removeChild(ElementName.AUDITPOOL, null, 0);
		final ProductHelper broc = xjdf2.appendProduct();
		broc.setDescriptiveName("Brochures");
		broc.setRoot();
		broc.setAmount(500);
		po.appendXJDF(xjdf2);

		pt.cleanUp();
		setSnippet(po.getRoot(), true);
		writeExample(pt, "businessobjects/PurchaseOrderCart.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testContentDelivery()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();
		final ContentDelivery po = (ContentDelivery) pt.appendRequest(EnumBusinessObject.ContentDelivery, null);
		po.setBusinessID("CD_1");
		po.setBusinessRefID("PO_1");
		po.setUpdateMethod(EnumUpdateMethod.Add);
		final XJDFHelper xjdf = new XJDFHelper("cart1.item1", null, null);
		xjdf.setTypes(JDFConstants.TYPE_PRODUCT);
		xjdf.addType(EnumType.Delivery);
		final ResourceHelper rh = xjdf.appendResourceSet(ElementName.RUNLIST, EnumUsage.Input).getCreatePartition(0, true);
		final JDFRunList rl = (JDFRunList) rh.getResource();
		rl.setFileSpecURL("https://myFileSource/pdfs/file1.pdf");
		po.setXJDF(xjdf);
		pt.cleanUp();
		setSnippet(po.getXJDF(0).getAuditPool().getRoot(), false);
		setSnippet(po.getXJDF(0).getSet(ElementName.NODEINFO, 0).getRoot(), false);
		setSnippet(po.getRoot(), true);
		writeExample(pt, "businessobjects/ContentDelivery.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testContentDeliveryResponse()
	{
		setFrom(false);
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();
		final ContentDeliveryResponse po = (ContentDeliveryResponse) pt.appendRequest(EnumBusinessObject.ContentDeliveryResponse, null);
		po.setBusinessID("CDR_1");
		po.setBusinessRefID("CD_1");
		po.setResult(EnumResult.Accepted);
		final AuditPoolHelper aph = po.getCreateAuditPool();
		final AuditHelper ah = aph.appendAudit("Resource");
		final MessageResourceHelper mrh = new MessageResourceHelper(ah.getRoot());
		final SetHelper set = mrh.getCreateSet();
		set.setName(ElementName.PREFLIGHTREPORT);
		set.getCreatePartition(0, true).getResource().setAttribute(AttributeName.ERRORCOUNT, "0");

		pt.cleanUp();
		setSnippet(po.getRoot(), true);
		writeExample(pt, "businessobjects/ContentDeliveryResponse.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testOrderStatusResponse()
	{
		setFrom(false);
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();
		final OrderStatusResponse po = (OrderStatusResponse) pt.appendRequest(EnumBusinessObject.OrderStatusResponse, null);
		po.setBusinessID("Staus_1");
		po.setBusinessRefID("PO_1");
		final AuditPoolHelper aph = po.getCreateAuditPool();
		po.setMilestone("j1", "ShippingInProgress");

		final AuditHelper ah = aph.appendAudit("Resource");
		final MessageResourceHelper mrh = new MessageResourceHelper(ah.getRoot());
		mrh.getCreateSet().setName(ElementName.DELIVERYPARAMS);
		final JDFDeliveryParams dp = (JDFDeliveryParams) mrh.getSet().getCreatePartition(0, true).getResource();
		dp.setAttribute(AttributeName.TRACKINGID, "T123");
		pt.cleanUp();
		setSnippet(po.getRoot(), true);
		writeExample(pt, "businessobjects/OrderStatus.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testQuotation()
	{
		setFrom(false);
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
		q2.setQuoteID("q2");
		q2.getCreatePricing().addPrice(EnumPriceType.Total, EnumTaxType.Net, "100 business cards", 42.00);
		q2.getCreatePricing().setCurrency("DKK");
		pt.cleanUp();
		setSnippet(quotation, true);
		writeExample(pt, "businessobjects/Quotation0.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testQuotationAdditional()
	{
		setFrom(false);
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();

		final Quotation quotation = (Quotation) pt.appendRequest(EnumBusinessObject.Quotation, null);
		quotation.setBusinessID("q1");
		quotation.setEstimate(false);
		quotation.setExpires(new JDFDate().setTime(18, 0, 0).addOffset(0, 0, 0, 14));
		final Quote q = quotation.appendQuote();

		q.setQuoteID("q1");
		q.getCreatePricing().setCurrency("GBP");

		final Price price1 = q.getCreatePricing().addPrice(EnumPriceType.Product, EnumTaxType.Net, "500 simple business cards", 250.00);
		price1.setAmount(500);
		price1.setLineID("L_1");
		price1.addAdditional(500, 250.0, 100, 40.00);

		final Price price2 = q.getCreatePricing().addPrice(EnumPriceType.Shipping, EnumTaxType.Net, "shipping and handling", 2.00);
		price2.addAdditional(1000, 2.00, 500, 1.00);
		price2.setLineID("L_2");

		final Price price3 = q.getCreatePricing().addPrice(EnumPriceType.Total, EnumTaxType.Net, "entire order", 252.00);
		price3.refPrice(price1);
		price3.refPrice(price2);

		pt.cleanUp();
		setSnippet(quotation, true);
		writeExample(pt, "businessobjects/QuotationAdditional.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testInvoiceAdditional()
	{
		setFrom(false);
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();

		final Invoice invoice = (Invoice) pt.appendRequest(EnumBusinessObject.Invoice, null);
		invoice.setBusinessID("i1");
		invoice.setBusinessRefID("q1");
		invoice.setDueDate(new JDFDate().setTime(18, 0, 0).addOffset(0, 0, 0, 14));
		invoice.getCreatePricing().setCurrency("GBP");

		final Price price1 = invoice.getCreatePricing().addPrice(EnumPriceType.Product, EnumTaxType.Net, "600 simple business cards", 290.00);
		price1.setAmount(600);

		final Price price2 = invoice.getCreatePricing().addPrice(EnumPriceType.Shipping, EnumTaxType.Net, "shipping and handling", 2.00);
		final Price pricetot = invoice.getCreatePricing().addPrice(EnumPriceType.Total, EnumTaxType.Net, "net total", 292);
		pricetot.refPrice(price1);
		pricetot.refPrice(price2);
		final Price taxtot = invoice.getCreatePricing().addPrice(EnumPriceType.Total, EnumTaxType.Tax, "20% tax total", 292 * 0.2);
		taxtot.refPrice(price1);
		taxtot.refPrice(price2);

		pt.cleanUp();
		setSnippet(invoice, true);
		writeExample(pt, "businessobjects/InvoiceAdditional.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testQuotationAmounts()
	{
		setFrom(false);
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();

		final Quotation quotation = (Quotation) pt.appendRequest(EnumBusinessObject.Quotation, null);
		quotation.setBusinessRefID("RFQ_Amount");
		quotation.setBusinessID("q1");
		quotation.setEstimate(false);
		quotation.setExpires(new JDFDate().setTime(18, 0, 0).addOffset(0, 0, 0, 14));
		final Quote q = quotation.appendQuote();
		q.setQuoteID("q1");
		final Price price1 = q.getCreatePricing().addPrice(EnumPriceType.Product, EnumTaxType.Net, "500 simple business cards", 250.00);
		price1.setAmount(500);
		price1.setLineID("L_1");
		price1.addAdditional(500, 250, 100, 40.00);
		price1.addAdditional(1000, 425, 500, 150.00);
		final Price price1t = q.getCreatePricing().addPrice(EnumPriceType.Total, EnumTaxType.Net, "total cost", 250.00);
		price1t.refPrice(price1);
		q.getCreatePricing().setCurrency("GBP");
		final XJDFHelper h1 = new XJDFHelper(EnumVersion.Version_2_0, "jobid");
		h1.setDescriptiveName("simple business cards");
		h1.addType(EnumType.Product);
		q.appendXJDF(h1);
		final Quote q2 = quotation.appendQuote();
		q2.setQuoteID("q2");
		final Price price2 = q2.getCreatePricing().addPrice(EnumPriceType.Product, EnumTaxType.Net, "500 varnished business cards", 350.00);
		price2.setAmount(500);
		price2.addAdditional(500, 350, 100, 50.00);
		price2.setLineID("L_2");
		price2.addAdditional(1000, 575, 500, 200.00);
		final Price price2t = q2.getCreatePricing().addPrice(EnumPriceType.Total, EnumTaxType.Net, "total cost", 350.00);
		price2t.refPrice(price2);
		q2.getCreatePricing().setCurrency("GBP");
		final XJDFHelper h2 = new XJDFHelper(EnumVersion.Version_2_0, "jobid");
		h2.setDescriptiveName("varnished business cards");
		h2.addType(EnumType.Product);
		q2.appendXJDF(h2);
		pt.cleanUp();
		setSnippet(quotation, true);
		final VElement v0 = pt.getRoot().getChildrenByTagName("XJDF", null, null, false, false, 0);
		for (final KElement e0 : v0)
		{
			final VElement v1 = e0.getChildElementVector(null, null);
			for (final KElement e : v1)
				setSnippet(e, false);
		}
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
	 *
	 */
	@Test
	public synchronized void testAmountPrices()
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();
		final RFQ rfq = (RFQ) pt.appendRequest(EnumBusinessObject.RFQ, null);
		rfq.setBusinessID("RFQ_Amount");
		final XJDFHelper xjdf = new XJDFHelper("J1", null);
		xjdf.removeSet(ElementName.NODEINFO);
		xjdf.getRoot().removeChild(ElementName.AUDITPOOL, null, 0);
		final ProductHelper bc = xjdf.appendProduct();
		bc.setDescriptiveName("BusinessCards");
		bc.setRoot();

		rfq.setExpiresDays(5);
		rfq.appendXJDF(xjdf);
		final JDFIntegerList amounts = new JDFIntegerList(new int[] { 1000, 5000 });
		rfq.setAmountPrices(amounts);
		assertEquals(amounts, rfq.getAmountPrices());
		pt.cleanUp();
		setSnippet(rfq, true);
		writeExample(pt, "businessobjects/RFQAmounts.ptk");
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
		setFrom(false);
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
		setFrom(false);
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		final PrintTalk pt = theFactory.getBuilder().getPrintTalk();

		final Refusal ref = (Refusal) pt.appendRequest(EnumBusinessObject.Refusal, null);
		ref.setBusinessRefID("PO_1");
		ref.setBusinessID("Refusal_1");
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
		po.setExpires(new JDFDate());
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
		setFrom(true);
		AbstractPrintTalk.setXJDFPrefix(true);

		super.setUp();
	}

	protected void setFrom(final boolean isCustomer)
	{
		final PrintTalkBuilderFactory theFactory = PrintTalkBuilderFactory.getTheFactory();
		theFactory.resetInstance();
		if (isCustomer)
		{
			theFactory.setFromURL("https://customer.com");
			theFactory.setToURL("https://printer.com");
		}
		else
		{
			theFactory.setToURL("https://customer.com");
			theFactory.setFromURL("https://printer.com");
		}
	}

}
