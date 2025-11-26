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

import java.util.HashSet;

import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.jdflib.util.StringUtil;
import org.cip4.printtalk.PrintTalkTestCase;
import org.junit.jupiter.api.Test;

public class PrintTalkSchemaTest extends PrintTalkTestCase
{

	class SchemaStamp
	{

		protected HashSet<String> fillIgnore()
		{
			final HashSet<String> s = new HashSet<>();
			s.add("Intent");
			s.add("PartWaste");
			s.add("Dependent");
			s.add("XJDF");
			s.add("ResourceSet");
			s.add("DeviceInfo");
			s.add("GangInfo");
			s.add("JobPhase");
			s.add("Header");
			s.add("MessageService");
			s.add("PipeParams");
			s.add("QueueEntry");
			s.add("QueueSubmissionParams");
			s.add("RequestQueueEntryParams");
			s.add("ResourceCmdParams");
			s.add("ResourceQuParams");
			s.add("ResubmissionParams");
			s.add("ReturnQueueEntryParams");
			s.add("Subscription");
			s.add("SubscriptionInfo");
			s.add("BundleItem/@Amount");
			s.add("Event");
			s.add("Icon");
			s.add("Milestone");
			s.add("Notification");
			s.add("ProcessRun");
			s.add("Module/@ModuleID");
			s.add("ColorantAlias");
			s.add("ComChannel");
			s.add("GeneralID");

			s.add("AmountPool/PartAmount");
			s.add("ProductList/Product");
			s.add("XJMF");
			s.add("Message");
			s.add("ResourceInfo");
			s.add("Audit/Header");
			s.add("ModifyQueueEntryParams");
			s.add("");
			return s;
		}

		void updateSchema(final int minor)
		{
			final String date = new JDFDate().getFormattedDateTime(JDFDate.DATEISO);
			final String commentText = " PrintTalk 2." + minor + " lax schema updated on " + date + " ";
			final XMLDoc d0 = XMLDoc.parseFile(getPTKSchema(minor));
			d0.getRoot().setXMLComment(commentText, false);
			d0.write2File(sm_dirTestDataTemp + "schema/printtalk.2_" + minor + "." + date + ".xsd", 2, false);

			final XMLDoc ds = XMLDoc.parseFile(getPTKSchema(minor));
			ds.getRoot().setXMLComment(StringUtil.replaceString(commentText, "lax", "strict"), false);
			final VElement vlax = ds.getRoot().getChildrenByTagName(null, null, new JDFAttributeMap("processContents", "lax"), false, false, 0);
			for (final KElement e : vlax)
				e.setAttribute("processContents", "strict");
			ds.write2File(sm_dirTestDataTemp + "schema/printtalk.strict.2_" + minor + "." + date + ".xsd", 2, false);

		}

		SchemaStamp()
		{
			super();
		}

	}

	/**
	 *
	 */
	@Test
	public synchronized void testSchemaUpdate()
	{
		new SchemaStamp().updateSchema(0);
		new SchemaStamp().updateSchema(1);
	}

}