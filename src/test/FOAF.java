/**	
 * test.FOAF
 * 
 * Version 0.1
 * 
 * 03/07/2008
 * 
 * Copyright (C) 2008 Stefano Bortoli for OKKAM FP7
 *	This program is free software; you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation; either version 2 of the License, or
 *	(at your option) any later version. 
 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *	GNU General Public License for more details.
 *	You should have received a copy of the GNU General Public License
 *	along with this program; if not, write to the Free Software
 *	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 **/

package test;
  
import com.hp.hpl.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from http://xmlns.com/foaf/spec/index.rdf 
 * @author Auto-generated by schemagen on 26 set 2007 16:19 
 */
public class FOAF {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabalary as a string ({@value})</p> */
    public static final String NS = "http://xmlns.com/foaf/0.1/";
    
    /** <p>The namespace of the vocabalary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabalary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>A personal mailbox, ie. an Internet mailbox associated with exactly one owner, 
     *  the first owner of this mailbox. This is a 'static inverse functional property', 
     *  in that there is (across time and change) at most one individual that ever 
     *  has any particular value for foaf:mbox.</p>
     */
    public static final Property mbox = m_model.createProperty( "http://xmlns.com/foaf/0.1/mbox" );
    
    /** <p>The sha1sum of the URI of an Internet mailbox associated with exactly one 
     *  owner, the first owner of the mailbox.</p>
     */
    public static final Property mbox_sha1sum = m_model.createProperty( "http://xmlns.com/foaf/0.1/mbox_sha1sum" );
    
    /** <p>The gender of this Agent (typically but not necessarily 'male' or 'female').</p> */
    public static final Property gender = m_model.createProperty( "http://xmlns.com/foaf/0.1/gender" );
    
    /** <p>A textual geekcode for this person, see http://www.geekcode.com/geek.html</p> */
    public static final Property geekcode = m_model.createProperty( "http://xmlns.com/foaf/0.1/geekcode" );
    
    /** <p>A checksum for the DNA of some thing. Joke.</p> */
    public static final Property dnaChecksum = m_model.createProperty( "http://xmlns.com/foaf/0.1/dnaChecksum" );
    
    /** <p>A sha1sum hash, in hex.</p> */
    public static final Property sha1 = m_model.createProperty( "http://xmlns.com/foaf/0.1/sha1" );
    
    /** <p>A location that something is based near, for some broadly human notion of 
     *  near.</p>
     */
    public static final Property based_near = m_model.createProperty( "http://xmlns.com/foaf/0.1/based_near" );
    
    /** <p>Title (Mr, Mrs, Ms, Dr. etc)</p> */
    public static final Property title = m_model.createProperty( "http://xmlns.com/foaf/0.1/title" );
    
    /** <p>A short informal nickname characterising an agent (includes login identifiers, 
     *  IRC and other chat nicknames).</p>
     */
    public static final Property nick = m_model.createProperty( "http://xmlns.com/foaf/0.1/nick" );
    
    /** <p>A jabber ID for something.</p> */
    public static final Property jabberID = m_model.createProperty( "http://xmlns.com/foaf/0.1/jabberID" );
    
    /** <p>An AIM chat ID</p> */
    public static final Property aimChatID = m_model.createProperty( "http://xmlns.com/foaf/0.1/aimChatID" );
    
    /** <p>An ICQ chat ID</p> */
    public static final Property icqChatID = m_model.createProperty( "http://xmlns.com/foaf/0.1/icqChatID" );
    
    /** <p>A Yahoo chat ID</p> */
    public static final Property yahooChatID = m_model.createProperty( "http://xmlns.com/foaf/0.1/yahooChatID" );
    
    /** <p>An MSN chat ID</p> */
    public static final Property msnChatID = m_model.createProperty( "http://xmlns.com/foaf/0.1/msnChatID" );
    
    /** <p>A name for some thing.</p> */
    public static final Property name = m_model.createProperty( "http://xmlns.com/foaf/0.1/name" );
    
    /** <p>The first name of a person.</p> */
    public static final Property firstName = m_model.createProperty( "http://xmlns.com/foaf/0.1/firstName" );
    
    /** <p>The given name of some person.</p> */
    public static final Property givenname = m_model.createProperty( "http://xmlns.com/foaf/0.1/givenname" );
    
    /** <p>The surname of some person.</p> */
    public static final Property surname = m_model.createProperty( "http://xmlns.com/foaf/0.1/surname" );
    
    /** <p>The family_name of some person.</p> */
    public static final Property family_name = m_model.createProperty( "http://xmlns.com/foaf/0.1/family_name" );
    
    /** <p>A phone, specified using fully qualified tel: URI scheme (refs: http://www.w3.org/Addressing/schemes.html#tel).</p> */
    public static final Property phone = m_model.createProperty( "http://xmlns.com/foaf/0.1/phone" );
    
    /** <p>A homepage for some thing.</p> */
    public static final Property homepage = m_model.createProperty( "http://xmlns.com/foaf/0.1/homepage" );
    
    /** <p>A weblog of some thing (whether person, group, company etc.).</p> */
    public static final Property weblog = m_model.createProperty( "http://xmlns.com/foaf/0.1/weblog" );
    
    /** <p>An OpenID for an Agent.</p> */
    public static final Property openid = m_model.createProperty( "http://xmlns.com/foaf/0.1/openid" );
    
    /** <p>A tipjar document for this agent, describing means for payment and reward.</p> */
    public static final Property tipjar = m_model.createProperty( "http://xmlns.com/foaf/0.1/tipjar" );
    
    /** <p>A .plan comment, in the tradition of finger and '.plan' files.</p> */
    public static final Property plan = m_model.createProperty( "http://xmlns.com/foaf/0.1/plan" );
    
    /** <p>Something that was made by this agent.</p> */
    public static final Property made = m_model.createProperty( "http://xmlns.com/foaf/0.1/made" );
    
    /** <p>An agent that made this thing.</p> */
    public static final Property maker = m_model.createProperty( "http://xmlns.com/foaf/0.1/maker" );
    
    /** <p>An image that can be used to represent some thing (ie. those depictions which 
     *  are particularly representative of something, eg. one's photo on a homepage).</p>
     */
    public static final Property img = m_model.createProperty( "http://xmlns.com/foaf/0.1/img" );
    
    /** <p>A depiction of some thing.</p> */
    public static final Property depiction = m_model.createProperty( "http://xmlns.com/foaf/0.1/depiction" );
    
    /** <p>A thing depicted in this representation.</p> */
    public static final Property depicts = m_model.createProperty( "http://xmlns.com/foaf/0.1/depicts" );
    
    /** <p>A derived thumbnail image.</p> */
    public static final Property thumbnail = m_model.createProperty( "http://xmlns.com/foaf/0.1/thumbnail" );
    
    /** <p>A Myers Briggs (MBTI) personality classification.</p> */
    public static final Property myersBriggs = m_model.createProperty( "http://xmlns.com/foaf/0.1/myersBriggs" );
    
    /** <p>A workplace homepage of some person; the homepage of an organization they 
     *  work for.</p>
     */
    public static final Property workplaceHomepage = m_model.createProperty( "http://xmlns.com/foaf/0.1/workplaceHomepage" );
    
    /** <p>A work info homepage of some person; a page about their work for some organization.</p> */
    public static final Property workInfoHomepage = m_model.createProperty( "http://xmlns.com/foaf/0.1/workInfoHomepage" );
    
    /** <p>A homepage of a school attended by the person.</p> */
    public static final Property schoolHomepage = m_model.createProperty( "http://xmlns.com/foaf/0.1/schoolHomepage" );
    
    /** <p>A person known by this person (indicating some level of reciprocated interaction 
     *  between the parties).</p>
     */
    public static final Property knows = m_model.createProperty( "http://xmlns.com/foaf/0.1/knows" );
    
    /** <p>A page about a topic of interest to this person.</p> */
    public static final Property interest = m_model.createProperty( "http://xmlns.com/foaf/0.1/interest" );
    
    /** <p>A thing of interest to this person.</p> */
    public static final Property topic_interest = m_model.createProperty( "http://xmlns.com/foaf/0.1/topic_interest" );
    
    /** <p>A link to the publications of this person.</p> */
    public static final Property publications = m_model.createProperty( "http://xmlns.com/foaf/0.1/publications" );
    
    /** <p>A current project this person works on.</p> */
    public static final Property currentProject = m_model.createProperty( "http://xmlns.com/foaf/0.1/currentProject" );
    
    /** <p>A project this person has previously worked on.</p> */
    public static final Property pastProject = m_model.createProperty( "http://xmlns.com/foaf/0.1/pastProject" );
    
    /** <p>An organization funding a project or person.</p> */
    public static final Property fundedBy = m_model.createProperty( "http://xmlns.com/foaf/0.1/fundedBy" );
    
    /** <p>A logo representing some thing.</p> */
    public static final Property logo = m_model.createProperty( "http://xmlns.com/foaf/0.1/logo" );
    
    /** <p>A topic of some page or document.</p> */
    public static final Property topic = m_model.createProperty( "http://xmlns.com/foaf/0.1/topic" );
    
    /** <p>The primary topic of some page or document.</p> */
    public static final Property primaryTopic = m_model.createProperty( "http://xmlns.com/foaf/0.1/primaryTopic" );
    
    /** <p>A document that this thing is the primary topic of.</p> */
    public static final Property isPrimaryTopicOf = m_model.createProperty( "http://xmlns.com/foaf/0.1/isPrimaryTopicOf" );
    
    /** <p>A page or document about this thing.</p> */
    public static final Property page = m_model.createProperty( "http://xmlns.com/foaf/0.1/page" );
    
    /** <p>A theme.</p> */
    public static final Property theme = m_model.createProperty( "http://xmlns.com/foaf/0.1/theme" );
    
    /** <p>Indicates an account held by this agent.</p> */
    public static final Property holdsAccount = m_model.createProperty( "http://xmlns.com/foaf/0.1/holdsAccount" );
    
    /** <p>Indicates a homepage of the service provide for this online account.</p> */
    public static final Property accountServiceHomepage = m_model.createProperty( "http://xmlns.com/foaf/0.1/accountServiceHomepage" );
    
    /** <p>Indicates the name (identifier) associated with this online account.</p> */
    public static final Property accountName = m_model.createProperty( "http://xmlns.com/foaf/0.1/accountName" );
    
    /** <p>Indicates a member of a Group</p> */
    public static final Property member = m_model.createProperty( "http://xmlns.com/foaf/0.1/member" );
    
    /** <p>Indicates the class of individuals that are a member of a Group</p> */
    public static final Property membershipClass = m_model.createProperty( "http://xmlns.com/foaf/0.1/membershipClass" );
    
    /** <p>The birthday of this Agent, represented in mm-dd string form, eg. '12-31'.</p> */
    public static final Property birthday = m_model.createProperty( "http://xmlns.com/foaf/0.1/birthday" );
    
    /** <p>A person.</p> */
    public static final Resource Person = m_model.createResource( "http://xmlns.com/foaf/0.1/Person" );
    
    /** <p>A document.</p> */
    public static final Resource Document = m_model.createResource( "http://xmlns.com/foaf/0.1/Document" );
    
    /** <p>An organization.</p> */
    public static final Resource Organization = m_model.createResource( "http://xmlns.com/foaf/0.1/Organization" );
    
    /** <p>A class of Agents.</p> */
    public static final Resource Group = m_model.createResource( "http://xmlns.com/foaf/0.1/Group" );
    
    /** <p>An agent (eg. person, group, software or physical artifact).</p> */
    public static final Resource Agent = m_model.createResource( "http://xmlns.com/foaf/0.1/Agent" );
    
    /** <p>A project (a collective endeavour of some kind).</p> */
    public static final Resource Project = m_model.createResource( "http://xmlns.com/foaf/0.1/Project" );
    
    /** <p>An image.</p> */
    public static final Resource Image = m_model.createResource( "http://xmlns.com/foaf/0.1/Image" );
    
    /** <p>A personal profile RDF document.</p> */
    public static final Resource PersonalProfileDocument = m_model.createResource( "http://xmlns.com/foaf/0.1/PersonalProfileDocument" );
    
    /** <p>An online account.</p> */
    public static final Resource OnlineAccount = m_model.createResource( "http://xmlns.com/foaf/0.1/OnlineAccount" );
    
    /** <p>An online gaming account.</p> */
    public static final Resource OnlineGamingAccount = m_model.createResource( "http://xmlns.com/foaf/0.1/OnlineGamingAccount" );
    
    /** <p>An online e-commerce account.</p> */
    public static final Resource OnlineEcommerceAccount = m_model.createResource( "http://xmlns.com/foaf/0.1/OnlineEcommerceAccount" );
    
    /** <p>An online chat account.</p> */
    public static final Resource OnlineChatAccount = m_model.createResource( "http://xmlns.com/foaf/0.1/OnlineChatAccount" );
    
}
