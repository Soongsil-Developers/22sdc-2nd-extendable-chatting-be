//package com.example.extendablechattingbe.chat;
//
//import com.example.extendablechattingbe.model.Chat;
//import com.example.extendablechattingbe.room.entity.Room;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//public interface ChatRepository extends JpaRepository<Chat, Long> {
//    @Query(value = "select c from Chat c join fetch c.user order by c.sendAt desc",
//            countQuery = "select count(c) from Chat c")
//    Page<Chat> findByRoomOrderBySendAtDesc(Room room, Pageable page);
//
//}
