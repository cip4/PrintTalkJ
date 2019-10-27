package org.cip4.printtalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.cip4.jdflib.auto.JDFAutoApprovalDetails.EnumApprovalState;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.KElement;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;
import org.junit.Test;

public class ProofApprovalResponseTest extends PrintTalkTestCase
{

	/**
	 *
	 *
	 */
	@Test
	public void testJobID()
	{
		final ProofApprovalResponse r = (ProofApprovalResponse) new PrintTalk().appendRequest(EnumBusinessObject.ProofApprovalResponse, null);
		r.setJobIDRef("j1");
		assertEquals("j1", r.getJobIDRef());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testApprovalDetails()
	{
		final ProofApprovalResponse r = (ProofApprovalResponse) new PrintTalk().appendRequest(EnumBusinessObject.ProofApprovalResponse, null);
		r.setJobIDRef("j1");
		final KElement e = r.getCreateApprovalDetails();
		e.setAttribute(AttributeName.APPROVALSTATE, EnumApprovalState.Approved.getName());
		assertNotNull(e);
		reparse(r, false);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testProofItem()
	{
		final ProofApprovalResponse r = (ProofApprovalResponse) new PrintTalk().appendRequest(EnumBusinessObject.ProofApprovalResponse, null);
		r.setJobIDRef("j1");
		final KElement e = r.getCreateProofItem();
		assertNotNull(e);
		final KElement e2 = r.getProofItem();
		assertEquals(e2, e);
		final KElement ad = r.getCreateApprovalDetails();
		ad.setAttribute(AttributeName.APPROVALSTATE, EnumApprovalState.Approved.getName());
		r.cleanUp();
		reparse(r, false);
	}

}
