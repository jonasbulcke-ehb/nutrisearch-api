package be.ehb.gdt.nutrisearch_api.controllers

import com.google.firebase.auth.FirebaseAuth
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1")
class AuthController(val firebaseAuth: FirebaseAuth) {
    @PostMapping("/authenticate")
    fun authenticate(userData: Map<String, String>): String {
//        val username = userData["username"] ?: throw IllegalArgumentException("No username provided")
//        val password = userData["password"] ?: throw IllegalArgumentException("No password provided")

        return ""
    }

    @GetMapping("/test")
    fun test(principal: Principal): String {
        return principal.name
    }
}