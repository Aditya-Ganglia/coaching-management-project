# 📄 Coaching Management System — Issue & Resolution Log

## 1️⃣ Issue Title: PatternParseException in Spring Security URL Matching

**Date Identified:**  
May 26, 2024

**Module Affected:**  
`SecurityConfig.java` — Spring Security Endpoint Protection

---

## 🛑 Problem Description
While securing API endpoints using `requestMatchers`, the application threw the following exception during runtime:

```
org.springframework.web.util.pattern.PatternParseException: 
No more pattern data allowed after {*...} or ** pattern element
```

This occurred specifically when trying to secure endpoints like:

```
.requestMatchers("/api/marks/summary/student/**").hasAuthority("student")
```

---

## 🔍 Root Cause Analysis
- Starting from **Spring Boot 3.x**, Spring internally switched to using **PathPatternParser** instead of Ant-style path matching by default.
- **PathPatternParser** is stricter and does not support classic Ant patterns like `/**` in certain contexts.
- Mixing patterns or using wildcards incorrectly led to this parsing exception.
- Our use of `requestMatchers` assumed the old behavior.

---

## ❌ Failed Attempts to Fix
- Tried switching to:

```
.requestMatchers(new AntPathRequestMatcher("/api/marks/summary/student/**"))
```
But this wasn't consistent and cluttered the config.

- Checked for deprecated methods and tried reordering matchers — issue persisted.

---

## ✅ Final Resolution
Explicitly disable the default **PathPatternParser** and revert to **Ant-style matching** by setting the parser to `null` in a `WebMvcConfigurer` bean.

**Code Added in `SecurityConfig.java`:**

```
@Bean
public WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void configurePathMatch(PathMatchConfigurer configurer) {
            configurer.setPatternParser(null);   // 🔧 Reverts to AntPathMatcher
        }
    };
}
```

This forced Spring to use the legacy **AntPathMatcher**, which supports patterns like `/**` without throwing parsing errors.

---

## 🎯 Outcome
- The application started successfully.
- All secured endpoints using `/**` patterns worked as expected.
- No further `PatternParseException` encountered.
