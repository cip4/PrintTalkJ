/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2021 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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

import java.io.File;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.extensions.AuditPoolHelper;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.util.FileUtil;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.OrderStatusResponse;
import org.cip4.printtalk.PrintTalk;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;
import org.cip4.printtalk.PrintTalkTestCase;
import org.cip4.printtalk.PurchaseOrder;
import org.cip4.printtalk.Refusal;
import org.cip4.printtalk.Refusal.EnumReason;
import org.cip4.printtalk.builder.PrintTalkBuilder;
import org.cip4.printtalk.builder.PrintTalkBuilderFactory;
import org.junit.jupiter.api.Test;

public class ExampleAppIcs extends PrintTalkTestCase
{

	/**
	 *
	 */
	@Test
	public synchronized void testParseExamples()
	{
		final File[] files = FileUtil.listFilesWithExtension(new File(sm_dirTestData + "appics"), "xml");
		for (final File f : files)
		{
			log.info(f.getName());
			schemaParse(f, 22);
		}
	}

	/**
	 *
	 */
	@Test
	public synchronized void testUpdateExamples()
	{
		final File[] files = FileUtil.listFilesWithExtension(new File(sm_dirTestData + "appics"), "xml");
		for (final File f : files)
		{
			log.info(f.getName());
			final PrintTalk ptk = PrintTalk.parseFile(f.getAbsolutePath());
			ptk.setAttribute(AttributeName.ICSVERSIONS, "Cus-APP_L1-2.2");
			ptk.setAttribute(AttributeName.VERSION, "2.2");
			ptk.init();
			ptk.write2File(sm_dirTestDataTemp + f.getName());
			schemaParse(new File(sm_dirTestDataTemp + f.getName()), 22);
		}
	}

	/**
	 *
	 */
	@Test
	public synchronized void testConfirmation()
	{
		final PrintTalkBuilder ptb = getBuilder(true);
		ptb.setBusinessObject(EnumBusinessObject.Confirmation);
		final PrintTalk pt = ptb.getPrintTalk();
		pt.getBusinessObject().setBusinessRefID("PO_ID");

		setSnippet(pt, true);
		writeExample(pt, "ics/app/confirmation.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testPO()
	{
		final PrintTalkBuilder ptb = getBuilder(true);
		ptb.setBusinessObject(EnumBusinessObject.PurchaseOrder);
		ptb.setCustomerID("CID-123");
		final PrintTalk pt = ptb.getPrintTalk();

		setSnippet(pt, true);
		final PrintTalk ptOld = PrintTalk.parseFile(sm_dirTestData + "appics/purchaseorder.xml");
		final PurchaseOrder poOld = (PurchaseOrder) ptOld.getBusinessObject();
		final XJDFHelper xjdf = poOld.getXJDF(0);
		final PurchaseOrder po = (PurchaseOrder) pt.getBusinessObject();
		po.setXJDF(xjdf);
		po.getRoot().copyElement(poOld.getPricing().getRoot(), null);
		po.setExpires(new JDFDate().setTime(18, 0, 0).addOffset(0, 0, 0, 14));
		po.setBusinessID("PO_ID");
		pt.cleanUp();
		writeExample(pt, "ics/app/purchase-order.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testOSR()
	{
		final PrintTalkBuilder ptb = getBuilder(false);
		ptb.setBusinessObject(EnumBusinessObject.OrderStatusResponse);
		ptb.setCustomerID("CID-123");

		final PrintTalk pt = ptb.getPrintTalk();
		setSnippet(pt, true);
		final PrintTalk ptOld = PrintTalk.parseFile(sm_dirTestData + "appics/orderstatusresponse.xml");
		final OrderStatusResponse poOld = (OrderStatusResponse) ptOld.getBusinessObject();
		final AuditPoolHelper ap = poOld.getCreateAuditPool();
		final OrderStatusResponse osr = (OrderStatusResponse) pt.getBusinessObject();
		osr.setJobIDRef("JOB-22");
		osr.setAuditPool(ap);
		osr.setBusinessRefID("PO_ID");
		pt.cleanUp();
		writeExample(pt, "ics/app/order-status-response.ptk");
	}

	/**
	 *
	 */
	@Test
	public synchronized void testRefusal()
	{
		final PrintTalkBuilder ptb = getBuilder(true);
		ptb.setBusinessObject(EnumBusinessObject.Refusal);
		final PrintTalk pt = ptb.getPrintTalk();
		final Refusal r = (Refusal) pt.getBusinessObject();
		r.setReason(EnumReason.InvalidPrice);
		r.setReasonDetails("WrongPrice");
		r.setBusinessRefID("PO_ID");

		setSnippet(pt, true);
		writeExample(pt, "ics/app/refusal.ptk");
	}

	protected PrintTalkBuilder getBuilder(final boolean isWorker)
	{
		final PrintTalkBuilder ptb = PrintTalkBuilderFactory.getTheFactory().getBuilder();
		ptb.resetInstance();
		ptb.setVersion(PrintTalk.getDefaultVersion());
		ptb.setIcsVersions(new VString("Cus-APP_L1-2.2"));
		if (isWorker)
		{
			ptb.setFromURL("https://worker.example.org/XJDF");
			ptb.setToURL("https://manager.example.org/XJDF");
		}
		else
		{
			ptb.setToURL("https://worker.example.org/XJDF");
			ptb.setFromURL("https://manager.example.org/XJDF");
		}
		return ptb;
	}

}