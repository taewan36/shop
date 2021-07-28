package com.vkrh0406.shop;

import com.vkrh0406.shop.domain.Category;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.domain.UploadFile;
import com.vkrh0406.shop.repository.CategoryRepository;
import com.vkrh0406.shop.repository.ItemRepository;
import com.vkrh0406.shop.repository.MemberRepository;
import com.vkrh0406.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void initCategories() {
        initService.initCategory();
        initService.initItemDB();
        initService.initMemberDb();

        initService.updateCategory();

    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final CategoryRepository categoryRepository;

        private final CategoryService categoryService;
        private final ItemRepository itemRepository;
        private final MemberRepository memberRepository;

        public void updateCategory(){
            categoryService.updateCategory();
        }

        public void initCategory() {


            List<Category> all = categoryRepository.findAll();

            Long sequance_id = 0L;

            if (all.size() >= 1) {
                return;
            }

            Category category1 = new Category("IT 모바일", 1L, 0L);
            categoryRepository.save(category1);

            Category category2 = new Category("자연과학", 2L, 0L);
            categoryRepository.save(category2);

            Category category3 = new Category("자기계발", 3L, 0L);
            categoryRepository.save(category3);

            Category category4 = new Category("게임", 4L, 1L);
            categoryRepository.save(category4);

            Category category5 = new Category("공학", 5L, 2L);
            categoryRepository.save(category5);

            Category category6 = new Category("처세술/삶의 자세", 6L, 3L);
            categoryRepository.save(category6);

            Category category7 = new Category("웹사이트", 7L, 1L);
            categoryRepository.save(category7);

            Category category8 = new Category("물리학", 8L, 2L);
            categoryRepository.save(category8);

            Category category9 = new Category("인간관계", 9L, 3L);
            categoryRepository.save(category9);





        }

        public void initMemberDb(){
            List<Member> all = memberRepository.findAll();
            if (all.size() != 0) {
                return;
            }
            Member member = new Member("test", "test!", "test", null);
            member.setAdmin(true);
            memberRepository.save(member);
        }


        public void initItemDB() {

            List<Item> all = itemRepository.findAll();
            if (all.size() > 0) {
                return;
            }

            Item item = new Item(5000, 0, 55, 5,
                    new UploadFile("업로드.png", "book1.jpg"), categoryService.findCategoryByName("게임")
                    , "로블록스 게임제작 무작정 따라하기");
            Item item2 = new Item(5000, 0, 55, 5,
                    new UploadFile("업로드.png", "book2.jpg"), categoryService.findCategoryByName("게임"),
                    "DEVELOPING 2D GAMES WITH UNITY");
            Item item3 = new Item(5000, 0, 55, 5,
                    new UploadFile("업로드.png", "book3.jpg"), categoryService.findCategoryByName("게임"),
                    "유니티 2D 게임 제작");
            Item item4 = new Item(5000, 0, 55, 5,
                    new UploadFile("업로드.png", "book4.jpg"), categoryService.findCategoryByName("게임"),
                    "로호의 배경 일러스트 메이킹");
            Item item5 = new Item(5000, 0, 55, 5,
                    new UploadFile("업로드.png", "book5.jpg"), categoryService.findCategoryByName("게임"),
                    "인생 유니티 VR/AR 교과서");
            Item item6 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "book6.jpg"), categoryService.findCategoryByName("게임"),
                    "언리얼 엔진 4");
            Item item7 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "book7.jpg"), categoryService.findCategoryByName("게임"),
                    "파이썬으로 배우는 게임 개발 실전편");
            Item item8 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "book8.jpg"), categoryService.findCategoryByName("게임"),
                    "게임 프로그래머로 산다는 것");

            itemRepository.save(item);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);

            initItemDB2();
            initItemDB3();
            initItemDB4();
            initItemDB5();
            initItemDB6();
            initItemDB7();



        }

        public void initItemDB2() {



            Item item = new Item(5000, 2000, 55, 1,
                    new UploadFile("업로드.png", "book1.jpg"), categoryService.findCategoryByName("게임")
                    , "로블록스 게임제작 무작정 따라하기2");
            Item item2 = new Item(5000, 2000, 55, 2,
                    new UploadFile("업로드.png", "book2.jpg"), categoryService.findCategoryByName("게임"),
                    "DEVELOPING 2D GAMES WITH UNITY2");
            Item item3 = new Item(5000, 2000, 55, 0,
                    new UploadFile("업로드.png", "book3.jpg"), categoryService.findCategoryByName("게임"),
                    "유니티 2D 게임 제작2");
            Item item4 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "book4.jpg"), categoryService.findCategoryByName("게임"),
                    "로호의 배경 일러스트 메이킹2");
            Item item5 = new Item(5000, 2000, 55, 4,
                    new UploadFile("업로드.png", "book5.jpg"), categoryService.findCategoryByName("게임"),
                    "인생 유니티 VR/AR 교과서2");
            Item item6 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "book6.jpg"), categoryService.findCategoryByName("게임"),
                    "언리얼 엔진 42");
            Item item7 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "book7.jpg"), categoryService.findCategoryByName("게임"),
                    "파이썬으로 배우는 게임 개발 실전편2");
            Item item8 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "book8.jpg"), categoryService.findCategoryByName("게임"),
                    "게임 프로그래머로 산다는 것2");

            itemRepository.save(item);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);


        }

        public void initItemDB3() {



            Item item = new Item(20000, 0, 55, 1,
                    new UploadFile("업로드.png", "RelationshipsBook1.jpg"), categoryService.findCategoryByName("인간관계")
                    , "데일 카네기 인간관계론");
            Item item2 = new Item(5000, 2000, 55, 2,
                    new UploadFile("업로드.png", "RelationshipsBook2.jpg"), categoryService.findCategoryByName("인간관계"),
                    "오은영의 화해");
            Item item3 = new Item(5000, 2000, 55, 0,
                    new UploadFile("업로드.png", "RelationshipsBook3.jpg"), categoryService.findCategoryByName("인간관계"),
                    "우리의 뇌는 어떻게 배우는가");
            Item item4 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "RelationshipsBook4.jpg"), categoryService.findCategoryByName("인간관계"),
                    "자존감 수업");
            Item item5 = new Item(5000, 2000, 55, 4,
                    new UploadFile("업로드.png", "RelationshipsBook5.jpg"), categoryService.findCategoryByName("인간관계"),
                    "백설마녀의 달콤살벌 연애레시피");
            Item item6 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "RelationshipsBook6.jpg"), categoryService.findCategoryByName("인간관계"),
                    "비울수록 사람을 더 채우는 말그릇");
            Item item7 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "RelationshipsBook7.jpg"), categoryService.findCategoryByName("인간관계"),
                    "모든 관계는 말투에서 시작된다");
            Item item8 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "RelationshipsBook8.jpg"), categoryService.findCategoryByName("인간관계"),
                    "말투에도 연습이 필요합니다");

            itemRepository.save(item);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);


        }

        public void initItemDB4() {



            Item item = new Item(20000, 0, 55, 1,
                    new UploadFile("업로드.png", "webbook1.jpg"), categoryService.findCategoryByName("웹사이트")
                    , "HTML + CSS + 자바스크립트 웹 표준의 정석");
            Item item2 = new Item(5000, 2000, 55, 2,
                    new UploadFile("업로드.png", "webbook2.jpg"), categoryService.findCategoryByName("웹사이트"),
                    "사용자를 끌어들이는 UX/UI의 비밀");
            Item item3 = new Item(5000, 2000, 55, 0,
                    new UploadFile("업로드.png", "webbook3.jpg"), categoryService.findCategoryByName("웹사이트"),
                    "스프링 부트와 AWS로 혼자 구현하는 웹 서비스");
            Item item4 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "webbook4.jpg"), categoryService.findCategoryByName("웹사이트"),
                    "이펙티브 타입스크립트");
            Item item5 = new Item(5000, 2000, 55, 4,
                    new UploadFile("업로드.png", "webbook5.jpg"), categoryService.findCategoryByName("웹사이트"),
                    "Node.js 교과서");
            Item item6 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "webbook6.jpg"), categoryService.findCategoryByName("웹사이트"),
                    "리액트를 다루는 기술");
            Item item7 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "webbook7.jpg"), categoryService.findCategoryByName("웹사이트"),
                    "사용자의 행동을 분석해 성과를 높이는 구글 애널리틱스");
            Item item8 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "webbook8.jpg"), categoryService.findCategoryByName("웹사이트"),
                    "생활코딩! HTML + CSS + 자바스크립트");

            itemRepository.save(item);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);


        }

        public void initItemDB5() {



            Item item = new Item(20000, 0, 55, 1,
                    new UploadFile("업로드.png", "engineeringBook1.jpg"), categoryService.findCategoryByName("공학")
                    , "우리 옛 건축에 담긴 표정들");
            Item item2 = new Item(5000, 2000, 55, 2,
                    new UploadFile("업로드.png", "engineeringBook2.jpg"), categoryService.findCategoryByName("공학"),
                    "빌 게이츠, 기후재앙을 피하는 법");
            Item item3 = new Item(5000, 2000, 55, 0,
                    new UploadFile("업로드.png", "engineeringBook3.jpg"), categoryService.findCategoryByName("공학"),
                    "탄소중립 지구와 화해하는 기술");
            Item item4 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "engineeringBook4.jpg"), categoryService.findCategoryByName("공학"),
                    "자동차 구조 교과서");
            Item item5 = new Item(5000, 2000, 55, 4,
                    new UploadFile("업로드.png", "engineeringBook5.jpg"), categoryService.findCategoryByName("공학"),
                    "과학의 씨앗");
            Item item6 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "engineeringBook6.jpg"), categoryService.findCategoryByName("공학"),
                    "카페의 공간학");
            Item item7 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "engineeringBook7.jpg"), categoryService.findCategoryByName("공학"),
                    "비행기 조종 교과서");
            Item item8 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "engineeringBook8.jpg"), categoryService.findCategoryByName("공학"),
                    "반도체 제대로 이해하기");

            itemRepository.save(item);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);


        }

        public void initItemDB6() {



            Item item = new Item(20000, 0, 55, 1,
                    new UploadFile("업로드.png", "physicsBook1.jpg"), categoryService.findCategoryByName("물리학")
                    , "김상욱의 양자 공부");
            Item item2 = new Item(5000, 2000, 55, 2,
                    new UploadFile("업로드.png", "physicsBook2.jpg"), categoryService.findCategoryByName("물리학"),
                    "시간은 흐르지 않는다");
            Item item3 = new Item(5000, 2000, 55, 0,
                    new UploadFile("업로드.png", "physicsBook3.jpg"), categoryService.findCategoryByName("물리학"),
                    "모든 순간의 물리학");
            Item item4 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "physicsBook4.jpg"), categoryService.findCategoryByName("물리학"),
                    "만약 시간이 존재하지 않는다면");
            Item item5 = new Item(5000, 2000, 55, 4,
                    new UploadFile("업로드.png", "physicsBook5.jpg"), categoryService.findCategoryByName("물리학"),
                    "법칙 원리 공식을 쉽게 정리한 물리화학 사전");
            Item item6 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "physicsBook6.jpg"), categoryService.findCategoryByName("물리학"),
                    "부분과 전체");
            Item item7 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "physicsBook7.jpg"), categoryService.findCategoryByName("물리학"),
                    "재미있는 물리 여행");
            Item item8 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "physicsBook8.jpg"), categoryService.findCategoryByName("물리학"),
                    "빛의 물리학");

            itemRepository.save(item);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);


        }

        public void initItemDB7() {



            Item item = new Item(20000, 0, 55, 1,
                    new UploadFile("업로드.png", "treatmentBook1.jpg"), categoryService.findCategoryByName("처세술/삶의 자세")
                    , "매력적인 물들고 싶은 남자");
            Item item2 = new Item(5000, 2000, 55, 2,
                    new UploadFile("업로드.png", "treatmentBook2.jpg"), categoryService.findCategoryByName("처세술/삶의 자세"),
                    "초 생산성");
            Item item3 = new Item(5000, 2000, 55, 0,
                    new UploadFile("업로드.png", "treatmentBook3.jpg"), categoryService.findCategoryByName("처세술/삶의 자세"),
                    "예민한 사람도 마음이 편안해지는 작은 습관");
            Item item4 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "treatmentBook4.jpg"), categoryService.findCategoryByName("처세술/삶의 자세"),
                    "평범해서 더욱 소중한 아주 보통의 행복");
            Item item5 = new Item(5000, 2000, 55, 4,
                    new UploadFile("업로드.png", "treatmentBook5.jpg"), categoryService.findCategoryByName("처세술/삶의 자세"),
                    "강원국의 어른답게 말합니다");
            Item item6 = new Item(5000, 2000, 55, 3,
                    new UploadFile("업로드.png", "treatmentBook6.jpg"), categoryService.findCategoryByName("처세술/삶의 자세"),
                    "시크릿");
            Item item7 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "treatmentBook7.jpg"), categoryService.findCategoryByName("처세술/삶의 자세"),
                    "손미나의 나의 첫 외국어 수업");
            Item item8 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "treatmentBook8.jpg"), categoryService.findCategoryByName("처세술/삶의 자세"),
                    "수도사처럼 생각하기");

            itemRepository.save(item);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);


        }



        //카테고리 DB


    }

}
