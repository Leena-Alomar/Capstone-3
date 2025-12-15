## 📌 Ammar

### 👤 User
- `POST` **/subscribe**  
  Subscribe a user

### 🎨 Logo
- `POST` **/logos/generate**  
  Generate logo options  
- `GET` **/logos**  
  Display generated logos  
- `PUT` **/logos/{id}/accept**  
  Accept selected logo  
- `PUT` **/logos/{id}/reject**  
  Reject selected logo  

### 📊 Campaign Analysis
- `GET` **/campaigns/expectations**  
  Get campaign expectations  
- `POST` **/audience/analyze**  
  Analyze target audience  
- `POST` **/campaigns/strategy**  
  Generate campaign strategy  

### 🗂 Projects & Campaigns
- `GET` **/projects**  
  Get projects of user  
- `GET` **/campaigns**  
  Get campaigns of user  

### 📝 Content
- `GET` **/content/draft**  
  Get content in draft state  
- `GET` **/content/approved**  
  Get approved content  
- `GET` **/content/published**  
  Get published content  

- `GET` **/content/platform/x**  
  Get content for X (Twitter)  
- `GET` **/content/platform/instagram**  
  Get content for Instagram  
- `GET` **/content/platform/tiktok**  
  Get content for TikTok  

---

Rand:
Worked on creating the AI intgration for many endpoints that generates content also summraize, translate, and evaluate it. worked on the logic of approving and rejecting a content and which to conect to the campain, created some scheduling features that complete a campaign and deleted rejected contents.

---

Leena:
created the endpoints related to packaging, the endpoints to AI generated target audience, worked on the subscription logic which allows non-subscribers to have limited acess.
