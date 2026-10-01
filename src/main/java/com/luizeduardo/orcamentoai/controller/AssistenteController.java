package com.luizeduardo.orcamentoai.controller;

import com.luizeduardo.orcamentoai.tools.FinanceiroTools;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/assistente")
public class AssistenteController {

    private final ChatClient chatClient;
    private final TranscriptionModel transcriptionModel;
    private final TextToSpeechModel textToSpeechModel;

    public AssistenteController(ChatClient.Builder builder,
                                FinanceiroTools financeiroTools,
                                TranscriptionModel transcriptionModel,
                                TextToSpeechModel textToSpeechModel) {
        this.chatClient = builder
                .defaultSystem("Você é um assistente financeiro. Use as ferramentas disponíveis quando a pessoa quiser registrar ou consultar dados financeiros. Responda sempre em português brasileiro e de forma objetiva.")
                .defaultTools(financeiroTools)
                .build();
        this.transcriptionModel = transcriptionModel;
        this.textToSpeechModel = textToSpeechModel;
    }

    @PostMapping("/texto")
    public String conversar(@RequestBody String mensagem) {
        return chatClient.prompt().user(mensagem).call().content();
    }

    @PostMapping(value = "/voz", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mp3")
    public ResponseEntity<Resource> conversarPorVoz(@RequestParam("file") MultipartFile file) {
        String texto = transcriptionModel.transcribe(file.getResource());
        String resposta = chatClient.prompt().user(texto).call().content();
        byte[] audio = textToSpeechModel.call(resposta);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("resposta.mp3").build().toString())
                .body(new ByteArrayResource(audio));
    }
}
