<%@ page language="java" contenttype="text/html; charset=UTF-8"
pageencoding="UTF-8" %>
<link rel="stylesheet" href="resources/css/feed/feed.css" />
<link rel="stylesheet" href="resources/css/feed/feedDetail.css" />
<script defer src="resources/js/feed/feed.js"></script>
<script defer src="resources/js/feed/feedDetail.js"></script>
<div id="selectBox">
  <select name="feed">
    <option value="" selected>최신순</option>
    <option value="">인기순</option>
  </select>
</div>
<div id="feed">
  <div class="feed_container">
    <div class="feed_title">
      <img src="resources/img/common/basic_profile.png" alt="기본프로필사진" />
      <span>testetstUser1</span><span>2020-01-03</span>
      <i class="fas fa-ellipsis-h"></i>
    </div>
    <div class="feed_img">
      <div class="previous">
        <i class="fas fa-chevron-left"></i>
      </div>
      <img src="resources/img/feed/demo3.PNG" />
      <img src="resources/img/feed/demo4.PNG" />
      <img src="resources/img/feed/demo3.PNG" />
      <div class="next">
        <i class="fas fa-chevron-right"></i>
      </div>
    </div>
    <div class="feed_content">
      <p><a href="#">#테스트용</a><a href="#">#hashtag</a></p>
      <p>
        2018년 3월 서비스를 시작한 눈누가 3년만에 새롭게 개편되었습니다.
        오늘부터 개편된 2.0 버전의 눈누 베타 서비스를 시작합니다. 사용하시면서
        오류나 불편한 점이 있다면 메일로 제보해주세요. 감사합니다!
      </p>
    </div>
    <div class="feed_functionbar">
      <i class="far fa-heart"></i><span>22</span> <i class="fal fa-comment"></i
      ><span>33</span>
      <i class="far fa-bookmark"></i>
    </div>
  </div>
</div>
<!-- feed -->
<div class="feed_overlay"></div>
<!-- feed_overlay  -->
