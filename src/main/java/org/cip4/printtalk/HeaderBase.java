/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2018 The International Cooperation for the Integration of
 * Processes in  Prepress, Press and Postpress (CIP4).  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        The International Cooperation for the Integration of
 *        Processes in  Prepress, Press and Postpress (www.cip4.org)"
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of
 *    Processes in  Prepress, Press and Postpress" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4",
 *    nor may "CIP4" appear in their name, without prior written
 *    permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For
 * details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR
 * THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the The International Cooperation for the Integration
 * of Processes in Prepress, Press and Postpress and was
 * originally based on software
 * copyright (c) 1999-2001, Heidelberger Druckmaschinen AG
 * copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the
 * Integration of Processes in  Prepress, Press and Postpress , please see
 * <http://www.cip4.org/>.
 *
 *
 */
package org.cip4.printtalk;

import java.util.Vector;

import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.util.ContainerUtil;

/**
 *  base class for From To and Sender
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public class HeaderBase extends AbstractPrintTalk
{
	/**
	 * @param header
	 */
	public HeaderBase(final KElement header)
	{
		super(header);
	}

	/**
	 *
	 * @param iskip
	 * @return
	 */
	public Credential getCredential(final int iskip)
	{
		final KElement e = getElement(Credential.ELEMENT_CREDENTIAL, iskip);
		return e == null ? null : new Credential(e);
	}

	/**
	 *
	 * set the Identity value of a credential
	 *
	 * @param domain of the credential
	 * @param identity
	 *
	 *
	 */
	public Credential setCredential(final String domain, final String identity)
	{
		final Credential c = getCreateCredential(domain);
		if (c != null)
		{
			c.setIdentity(identity);
		}
		return c;
	}

	/**
	 *
	 * @param domain
	 * @return
	 */
	public Credential getCredential(final String domain)
	{
		final KElement e = theElement == null ? null : theElement.getChildWithAttribute(Credential.ELEMENT_CREDENTIAL, "domain", null, domain, 0, true);
		return e == null ? null : new Credential(e);
	}

	/**
	 *
	 * @param domain
	 * @return
	 */
	public Credential getCreateCredential(final String domain)
	{
		if (theElement == null)
			return null;

		final KElement e = theElement.getChildWithAttribute(Credential.ELEMENT_CREDENTIAL, "domain", null, domain, 0, true);
		if (e == null)
		{
			final Credential c = new Credential(theElement.appendElement(Credential.ELEMENT_CREDENTIAL));
			c.setDomain(domain);
		}
		return getCredential(domain);
	}

	/**
	 *
	 * @author rainer prosi
	 * @date Oct 28, 2013
	 */
	public enum EnumHeaderType
	{
		/** */
		From,
		/** */
		To,
		/** */
		Sender
	}

	/**
	 * @param domain
	 * @return
	 */
	public String getCredentialIdentity(final String domain)
	{
		final Credential c = getCredential(domain);
		return c == null ? null : c.getIdentity();
	}

	/**
	 * @param domain
	 * @return
	 */
	public JDFAttributeMap getCredentialMap()
	{
		final Vector<Credential> v = getCredentials();
		final JDFAttributeMap map = new JDFAttributeMap();
		if (v != null)
		{
			for (final Credential c : v)
			{
				final String id = c.getIdentity();
				if (id != null)
				{
					final String domain = c.getDomain();
					map.put(domain == null ? "" : domain, id);

				}
			}
		}
		return map.isEmpty() ? null : map;
	}

	public Vector<Credential> getCredentials()
	{
		final VElement v0 = theElement == null ? null : theElement.getChildElementVector(Credential.ELEMENT_CREDENTIAL, null);
		if (!ContainerUtil.isEmpty(v0))
		{
			final Vector<Credential> v = new Vector<>();
			for (final KElement e : v0)
			{
				v.add(new Credential(e));
			}
			return v;
		}
		return null;
	}

}
