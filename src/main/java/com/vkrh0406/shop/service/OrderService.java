package com.vkrh0406.shop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkrh0406.shop.domain.*;
import com.vkrh0406.shop.dto.OrderDto;
import com.vkrh0406.shop.form.OrderForm;
import com.vkrh0406.shop.form.PayForm;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.repository.MemberRepository;
import com.vkrh0406.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;


    //결제 검증 프로세스
    @Transactional
    public Map<String,Object> payCheckProcess(String request) throws JsonProcessingException {

        //들어온 json 파싱
        HashMap<String, String> requestMap = objectMapper.readValue(request, new TypeReference<HashMap<String, String>>() {});

        log.info("결제 검증 프로세스 시작");

        //리턴할 메시지
        Map<String, Object> message = new HashMap<>();
        String access_token=null;

        //액세스 토큰 획득후 json -> map 으로 바꾸는 과정
        try {
            ResponseEntity<String> accessTokenData = getAccessToken();
            String body = accessTokenData.getBody().toString();
            Map<String, String> response = (Map<String, String>) objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {
            }).get("response");

            //액세스 토큰 획득
            access_token = response.get("access_token");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }


        log.info("액세스 토큰 {}",access_token );

        //imp_uid
        String imp_uid = requestMap.get("imp_uid");
        //order_id
        String order_id = requestMap.get("merchant_uid");

        Map<String, String> paymentDataResponse;
        try {
            //액세스 토큰을 이용하여 결제 내역 조회
            ResponseEntity<String> paymentData = getPaymentData(imp_uid, access_token);
            String paymentDataBody = paymentData.getBody();
            //paymentData json을 map으로 파싱
            Map<String, Object> paymentDataAll = objectMapper.readValue(paymentDataBody, new TypeReference<Map<String, Object>>() {
            });
            log.info("paymentData= {}",paymentDataAll);
            //Response 값을 얻기 위해 Object를 Map으로 형변환
             paymentDataResponse = (Map<String, String>) paymentDataAll.get("response");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }

        //실제 결제 가격
        String amount =String.valueOf(paymentDataResponse.get("amount"));
        int priceAmount = Integer.parseInt(amount);

        log.info("실제 결제 가격 {}", amount);

        Order order = orderRepository.findOrderByUuid(order_id).orElseThrow(() -> new IllegalStateException("이 uuid와 일치하는 오더가 없습니다"));

        // 실제 가격과 결제 가격이 일치하면 결제 성공
        if (priceAmount == order.getTotalPrice()) {

            order.setOrderStatus(OrderStatus.ORDER);

            message.put("code", "success");
            message.put("message", "결제 성공!");


            return message;
        }else{
            message.put("code", "fail");
            message.put("message", "가격이 불일치합니다.");
            return message;
        }

    }

    // imp_uid로 아임포트 서버에서 결제 취소
    private ResponseEntity<String> cancelPay(String imp_uid, String merchant_uid, String access_token) throws JsonProcessingException {

        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        //JSON 바인딩
        Map<String, Object> map = new HashMap<>();
        String params1 = objectMapper.writeValueAsString(map);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(params1, httpHeaders);

        //RestTemplate의 exchange 메소드를 통해 URL에 HttpEntity와 함께 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.iamport.kr/payments/" + imp_uid, HttpMethod.GET,
                entity, String.class);


        return responseEntity;

    }


    // imp_uid로 아임포트 서버에서 결제 정보 조회
    private ResponseEntity<String> getPaymentData(String imp_uid,String access_token) throws JsonProcessingException {

        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        httpHeaders.set("Authorization",access_token);

        //JSON 바인딩
        Map<String, Object> map = new HashMap<>();
        String params1 = objectMapper.writeValueAsString(map);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(params1, httpHeaders);

        //RestTemplate의 exchange 메소드를 통해 URL에 HttpEntity와 함께 요청
        RestTemplate restTemplate = new RestTemplate();
        log.info("imp_uid= {}", imp_uid);
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.iamport.kr/payments/"+imp_uid, HttpMethod.GET,
                entity, String.class);


        return responseEntity;

    }

    // 아이앰포트 api 호출 액세스 토큰값 가져오기
    private ResponseEntity<String> getAccessToken() throws JsonProcessingException {

        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //JSON 바인딩
        Map<String, Object> map = new HashMap<>();
        map.put("imp_key", "0613765197661947");
        map.put("imp_secret", "c546efb7c85585f7a3e52a287fa0bed7456baad83a2663490a8d3bf1768d4310ecd9174522007993");
        String params1 = objectMapper.writeValueAsString(map);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(params1, httpHeaders);

        //RestTemplate의 exchange 메소드를 통해 URL에 HttpEntity와 함께 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.iamport.kr/users/getToken", HttpMethod.POST,
                entity, String.class);


        return responseEntity;

    }






    //해당 멤버 오더리스트 뽑기
    public List<OrderDto> findAllByMemberId(Member member) {
        Member findMember = memberRepository.findMemberById(member.getId()).orElseThrow(() -> new IllegalStateException("이 멤버 id를 찾을수 없습니다."));



        //id desc 순으로 orderDto 리스트 뽑기
        List<OrderDto> result = orderRepository.findOrdersByMemberIdOrderByIdDesc(findMember.getId())
                .stream()
                .map(o -> new OrderDto(o.getId(), o.getTotalPrice(), o.getOrderDate(), o.getOrderStatus(), o.getDelivery(), o.getOrderItems()))
                .collect(Collectors.toList());

        return result;
    }

    //pay
    @Transactional
    public void payOrder(Long orderId, PayForm payForm) {
        Order findOrder = findOrderById(orderId);
        findOrder.getDelivery().setAddress(new Address(payForm.getAddrDetail(), payForm.getAddrPart1(), payForm.getAddrPart2(), payForm.getZipcode()));
        findOrder.setOrderStatus(OrderStatus.ORDER);
        findOrder.setOrderDate(LocalDateTime.now());

    }


    // 오더id로 오더 찾기
    public Order findOrderById(Long orderId) {
        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new IllegalStateException("이런 orderId는 존재하지 않습니다"));
        return order;
    }

    /// 오더 생성
    @Transactional
    public Long makeOrder(List<OrderForm> orderForms, Member member, HttpSession session) {


        //해당 멤버 찾기
        Member findMember = memberRepository.findMemberById(member.getId()).orElseThrow(() -> new IllegalStateException("이 멤버 id는 존재하지 않습니다"));
        //해당 멤버의 카트
        Cart cart = findMember.getCart();



        //오더 생성
        Order order = Order.CreateOrder(findMember, new Delivery(findMember.getAddress(), DeliveryStatus.READY), cart.getOrderItems());

        //오더 db에 저장
        orderRepository.save(order);

        //카트에서 orderItem들 꺼내기
        List<OrderItem> orderItems = cart.getOrderItems();

        //카트 지우고 다시 세션에 집어넣기
        orderItems.clear();
        long cartId = cart.getId();
        //orderRepository.deleteOrdersByCartId(cartId);

        cart.setSize(cart.getOrderItems().size());

        session.removeAttribute(SessionConst.SESSION_CART);
        session.setAttribute(SessionConst.SESSION_CART,cart);

        return order.getId();



    }

    // Order들을 OrderDto로 바꿔서 리턴
    public List<OrderDto> findAllOrder() {

        return orderRepository.findAll().stream()
                .map(o -> new OrderDto(o.getId(), o.getTotalPrice(), o.getOrderDate(), o.getOrderStatus(), o.getDelivery(), o.getOrderItems()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancel(Long orderId, Member member) {
        // orderId로 오더 가져오기
        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new IllegalStateException("이런 orderId는 없습니다."));

        //이 오더의 주인이 해당한 멤버가 아니면?
        if (!order.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("세션 멤버가 이 오더의 주인이 아닙니다.");
        }

        order.cancel();


    }

    //오더 상세 내용 가져오기
    public OrderDto getOrderContent(Long orderId, Member member) {
        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new IllegalStateException("이런 orderId는 없습니다"));

        //이 오더의 주인이 해당 멤버가 아닐경우
        if (!order.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("이 멤버는 오더의 주인이 아닙니다.");
        }

        OrderDto orderDto = new OrderDto(order.getId(), order.getTotalPrice(), order.getOrderDate(), order.getOrderStatus(), order.getDelivery(), order.getOrderItems());

        return orderDto;
    }
}
