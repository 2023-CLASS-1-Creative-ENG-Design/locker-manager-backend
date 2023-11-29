package knu.cse.locker.manager.test;

import io.swagger.v3.oas.annotations.tags.Tag;
import knu.cse.locker.manager.infra.blockchain.BlockChainAPI;
import knu.cse.locker.manager.infra.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테스트")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final EmailService emailService;
    private final BlockChainAPI blockChainAPI;


}
