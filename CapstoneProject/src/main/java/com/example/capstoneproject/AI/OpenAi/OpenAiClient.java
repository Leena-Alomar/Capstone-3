package com.example.capstoneproject.AI.OpenAi;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiClient {

    private final RestClient restClient;
    private final AiProperties props;

    public OpenAiClient(AiProperties props) {
        this.props = props;
        this.restClient = RestClient.builder()
                .baseUrl(props.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + props.getApiKey())
                .build();
    }


    public String calculateContentEngagementScore(String post_text) {
        Map<String, Object> requestBody = Map.of(
                "model", props.getModel(),
                "messages", List.of(
                        Map.of("role", "system",
                                "content", """
                                        Evaluate the engagement potential of the following post content.
                                       
                                        
                                        Rubric (0-2 each, total /10):
                                        1) hook_strength
                                        2) question_quality
                                        3) call_to_action_strength
                                        4) clarity_specificity
                                        5) social_triggers
                                        
                                        Rules:
                                        - Score each category 0,1,2 only.
                                        - Provide total_score = sum (0-10).
                                        - Provide flags: has_question, has_clear_cta, cta_type.
                                        - Provide 3 improvement suggestions that increase engagement but do not use clickbait or misinformation.
                                        - Output JSON only.
                                        
                                        Post:
                                        ""\"
                                        {post_text}
                                        ""\"
                                        
                                        """),
                        Map.of("role", "user",
                                "content",
                                "the post content is:- \n\n"+post_text+" .")
                ),
                "max_tokens", 3000
        );
        Map<String, Object> response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // ⚠️ Very simplified parsing – in real code map to a proper DTO
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        return (String) message.get("content");
    }

    public String evaluateContent(String post_text) {
        Map<String, Object> requestBody = Map.of(
                "model", props.getModel(),
                "messages", List.of(
                        Map.of("role", "system",
                                "content", """
                                        You are a social media content evaluator. Your job is to evaluate ONLY the content of a post (text), and estimate its engagement potential (comments/replies/shares) out of 10. Do NOT use any real performance metrics.
                                     
                                        
                                        Scoring rubric (0–2 points each, total 0–10):
                                        1) Hook strength (first line grabs attention)
                                        2) Question quality (invites replies; easy to answer)
                                        3) Call-to-action strength (clear ask: comment, vote, tag, share, DM, etc.)
                                        4) Clarity & specificity (clear topic, concrete details, not vague)
                                        5) Social triggers (relatable, curiosity, value, story, light debate—no misinformation/clickbait)
                                        
                                        Rules:
                                        - Give each category a score of ONLY: 0, 1, or 2.
                                        - total_score must equal the sum of category scores.
                                        - Identify the post style: {educational, story, announcement, opinion, question, promo, other}.
                                        - Detect whether the post contains a real question and a clear CTA.
                                        - Provide exactly 3 rewritten versions of the post:
                                          - Version A: optimized for comments
                                          - Version B: optimized for shares
                                          - Version C: optimized for clicks (without clickbait)
                                        - Provide 5 short improvement suggestions (actionable, not generic).
                                        - If the post is in Arabic, keep outputs in Arabic. If mixed, respond in the same dominant language.
                                        - Output MUST be valid JSON only. No extra text.
                                        
                                        Post content to evaluate:
                                        ""\"
                                        {post_text}
                                        ""\"
                                        """),
                        Map.of("role", "user",
                                "content",
                                "the post content is:- \n\n"+post_text+" .")
                ),
                "max_tokens", 3000
        );
        Map<String, Object> response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // ⚠️ Very simplified parsing – in real code map to a proper DTO
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        return (String) message.get("content");
    }

    public String compairConetent(String post_a_text, String post_b_text) {
        Map<String, Object> requestBody = Map.of(
                "model", props.getModel(),
                "messages", List.of(
                        Map.of("role", "system",
                                "content", """
                                        You are a social media content evaluator. Your task is to compare TWO post contents and determine which one is more likely to generate engagement (comments, replies, shares, saves) based ONLY on the content quality, not on any performance metrics.
                                        
                                        
                                        Evaluation rubric (0–2 points each, total 0–10 per post):
                                        1) Hook strength (first line grabs attention)
                                        2) Question quality (invites replies; easy to answer)
                                        3) Call-to-action strength (clear ask: comment, vote, tag, share, DM, etc.)
                                        4) Clarity & specificity (clear topic, concrete details, not vague)
                                        5) Social triggers (relatable, curiosity, value, story, light debate—no clickbait)
                                        
                                        Rules:
                                        - Score each category using ONLY: 0, 1, or 2.
                                        - Calculate total_score for each post as the sum of category scores.
                                        - Scores must be consistent and fair between the two posts.
                                        - Identify post style for each post (educational, story, opinion, question, announcement, promo, other).
                                        - Determine which post is stronger overall and explain why in 2–3 sentences.
                                        - Provide improvement suggestions for BOTH posts.
                                        - If the posts are in Arabic, respond in Arabic. If mixed, respond in the dominant language.
                                        - Output MUST be valid JSON only. No extra text.
                                        
                                        Post A:
                                        ""\"
                                        {post_a_text}
                                        ""\"
                                        
                                        Post B:
                                        ""\"
                                        {post_b_text}
                                        ""\"
                                        
                                        """),
                        Map.of("role", "user",
                                "content",
                                "the content of the first post is:- \n\n"+post_a_text+" .\n\n" +
                                        "the content of the second post is:- \n\n"+post_b_text+" .")
                ),
                "max_tokens", 3000
        );
        Map<String, Object> response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // ⚠️ Very simplified parsing – in real code map to a proper DTO
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        return (String) message.get("content");
    }

    public String campaignExpectations(String descreption,Integer duration, String name,String platform,Double budget) {
        Map<String, Object> requestBody = Map.of(
                "model", props.getModel(),
                "messages", List.of(
                        Map.of("role", "system",
                                "content", """
                                        You are a digital marketing and social media strategy analyst. Your task is to analyze a campaign and estimate its expected performance and engagement outcomes based ONLY on the campaign inputs and content quality, not historical analytics.
                                        
                                         Evaluation dimensions (score 0–2 each unless stated otherwise):
                                         1) Message clarity & consistency
                                         2) Audience–content fit
                                         3) Engagement drivers (questions, CTAs, interaction prompts)
                                         4) Value proposition (education, entertainment, utility)
                                         5) Emotional & social triggers (relatability, curiosity, urgency)
                                         6) Content diversity across posts (0–2)
                                         7) Posting cadence suitability (0–2)
                                        
                                         Rules:
                                         - Assign scores per dimension and compute an overall campaign_score (0–14).
                                         - Estimate expected engagement level: low / medium / high.
                                         - Estimate expected audience behavior:
                                           - comments likelihood
                                           - shares likelihood
                                           - passive consumption likelihood
                                         - Provide expected engagement range (qualitative, not numeric):
                                           - below average / average / above average / exceptional
                                         - Identify top 3 strengths and top 3 risks.
                                         - Provide 5 actionable recommendations to improve results.
                                         - Include a short expectations summary written for a non-technical stakeholder.
                                         - If campaign language is Arabic, respond in Arabic. If mixed, respond in dominant language.
                                         - Output MUST be valid JSON only. No explanations outside JSON.
                                        
                                        """),
                        Map.of("role", "user",
                                "content",
                                "the campaign informations is:- \n\ndescription: "+descreption+"\n name: "+name+"\nduration: " +duration+ "\nplatform: "+platform+"\nbudget: "+budget+" .")
                ),
                "max_tokens", 3000
        );
        Map<String, Object> response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // ⚠️ Very simplified parsing – in real code map to a proper DTO
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        return (String) message.get("content");
    }

    public String strategy(Integer min_age,Integer max_age,String gender, String intreset, String location, String income_level) {
        Map<String, Object> requestBody = Map.of(
                "model", props.getModel(),
                "messages", List.of(
                        Map.of("role", "system",
                                "content", """
                                        You are a digital marketing and audience strategy expert. Your task is to recommend content and engagement strategies tailored specifically to a given target audience, based on their characteristics, behavior, and preferences.
                                        
                                       
                                        Rules:
                                        - Base recommendations ONLY on the given audience profile.
                                        - Avoid generic advice; make strategies specific and actionable.
                                        - Suggest strategies that are realistic for the given platform(s).
                                        - If the language is Arabic, respond in Arabic.
                                        - Output MUST be valid JSON only.
                                        
                                        Return JSON in the following structure:
                                        
                                        """),
                        Map.of("role", "user",
                                "content",
                                "the campaign informations is:- \n\nage between: "+min_age+" and "+max_age+"\n gender: "+gender+"\ninterests: " +intreset+ "\nlocation: "+location+"\nincome level: "+income_level+" .")
                ),
                "max_tokens", 3000
        );
        Map<String, Object> response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // ⚠️ Very simplified parsing – in real code map to a proper DTO
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        return (String) message.get("content");
    }

}
