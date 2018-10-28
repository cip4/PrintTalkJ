/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2014 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
package org.cip4.printtalk;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.KElement;
import org.w3c.dom.Comment;
import org.w3c.dom.Node;

import junit.framework.TestCase;

/**
 * testcase base class
 *
 * @author rainer prosi
 * @date Jun 13, 2014
 */
public abstract class PrintTalkTestCase extends TestCase
{
	static protected final EnumVersion defaultVersion = EnumVersion.Version_1_5;
	static protected final String sm_dirTestData = getTestDataDir();
	static protected final String sm_dirTestSchema = sm_dirTestData + "schema" + File.separator + defaultVersion + File.separator;
	static protected final String sm_dirTestDataTemp = sm_dirTestData + "temp" + File.separator;

	private static String getTestDataDir()
	{

		String path = "test" + File.separator + "data";
		final File dataFile = new File(path);
		if (!dataFile.isDirectory()) // legacy - pre maven file structure support
			path = PrintTalkTestCase.class.getResource("/data").getPath();
		path = FilenameUtils.normalize(path) + File.separator;

		return path;

	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception
	{
		PrintTalk.setDefaultVersion(20);
		super.tearDown();
	}

	/**
	 *
	 * @param e
	 * @param startFirst if true include the enclosing element, if false exclude it
	 */
	protected void setSnippet(final KElement e, final boolean startFirst)
	{
		if (e != null)
		{
			final Node parent = e.getParentNode();
			final String start = " START SNIPPET ";
			final String end = " END SNIPPET ";
			Comment newChild = e.getOwnerDocument().createComment(startFirst ? start : end);
			parent.insertBefore(newChild, e);
			newChild = e.getOwnerDocument().createComment(startFirst ? end : start);
			parent.insertBefore(newChild, e.getNextSibling());
		}
	}

	/**
	 *
	 * @param pt
	 */
	protected void writeExample(final PrintTalk pt, final String file)
	{
		// TODO add schema parsing
		pt.write2File(sm_dirTestDataTemp + "Example/" + file);
	}

}
