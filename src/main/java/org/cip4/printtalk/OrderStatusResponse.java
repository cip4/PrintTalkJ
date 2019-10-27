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
package org.cip4.printtalk;

import org.cip4.jdflib.auto.JDFAutoNotification.EnumClass;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.extensions.AuditHelper;
import org.cip4.jdflib.extensions.AuditPoolHelper;
import org.cip4.jdflib.extensions.AuditResourceHelper;
import org.cip4.jdflib.resource.JDFMilestone;
import org.cip4.jdflib.resource.JDFNotification;

/**
 *
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public class OrderStatusResponse extends BusinessObject
{

	/**
	 *
	 * @param theElement
	 */
	public OrderStatusResponse(final KElement theElement)
	{
		super(theElement);
	}

	/**
	 *
	 *
	 * set a milestone
	 *
	 * @param jobID
	 * @param milestone
	 */
	public JDFNotification setMilestone(final String jobID, final String milestone)
	{
		setJobIDRef(jobID);
		final AuditPoolHelper auditPoolHelper = getCreateAuditPool();
		final JDFNotification notification = (JDFNotification) auditPoolHelper.appendAudit(ElementName.NOTIFICATION).getRoot().appendElementRaw(ElementName.NOTIFICATION, null);
		notification.setJobID(jobID);
		notification.appendMilestone().setMilestoneType(milestone);
		notification.removeAttribute(AttributeName.TYPE, null);
		notification.setClass(EnumClass.Event);
		auditPoolHelper.cleanUp();
		return notification;
	}

	/**
	 *
	 *
	 * set a milestone
	 *
	 * @param jobID
	 * @param milestone
	 */
	public AuditResourceHelper appendResourceAudit(final String jobID, final String resName)
	{
		setJobIDRef(jobID);
		final AuditPoolHelper auditPoolHelper = getCreateAuditPool();
		final AuditHelper appendAudit = auditPoolHelper.appendAudit("AuditResource");
		final AuditResourceHelper h = new AuditResourceHelper(appendAudit.getRoot());
		h.appendSet(resName);
		return h;
	}

	/**
	 * get job id ref value
	 *
	 * @return
	 */
	public String getJobIDRef()
	{
		return getAttribute(ATTR_JOBIDREF);
	}

	/**
	 * set job id ref value
	 *
	 * @param s
	 */
	public void setJobIDRef(final String s)
	{
		setAttribute(ATTR_JOBIDREF, s);
	}

	/**
	 *
	 *
	 * set a milestone
	 *
	 * @param nSkip
	 * @return the matching milestone notification parent element - null if it does not exist
	 */
	public JDFNotification getMilestoneNotification(final int nSkip)
	{
		return (JDFNotification) getRoot().getChildWithAttribute(ElementName.NOTIFICATION, AttributeName.CLASS, null, "*", nSkip, false);
	}

	/**
	 *
	 * @param n
	 * @return
	 */
	public String getMilestoneName(final int n)
	{
		final JDFNotification milestoneNotification = getMilestoneNotification(n);
		final JDFMilestone milestone = milestoneNotification != null ? milestoneNotification.getMilestone() : null;
		return milestone != null ? milestone.getMilestoneType() : null;
	}

	/**
	 * @see org.cip4.printtalk.AbstractPrintTalk#getCreateAuditPool()
	 */
	@Override
	public AuditPoolHelper getCreateAuditPool()
	{
		return super.getCreateAuditPool();
	}

	/**
	 * @see org.cip4.printtalk.AbstractPrintTalk#cleanUp()
	 */
	@Override
	public void cleanUp()
	{
		final AuditPoolHelper ah = getAuditPool();
		if (ah != null)
			ah.cleanUp();
		super.cleanUp();
	}

}
