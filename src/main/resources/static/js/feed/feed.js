'use strict'

function previousImg(e) {
  const feed_imgListDiv = e.parentNode.querySelector('.feed_imgList')
  let first_img = feed_imgListDiv.firstChild.nextSibling
  while (first_img.nodeType !== 1) {
    first_img = first_img.nextSibling
  }
  let last_img = feed_imgListDiv.lastChild.previousSibling
  while (last_img.nodeType !== 1) {
    last_img = last_img.previousSibling
  }
  first_img.before(last_img)
}

function nextImg(e) {
  const feed_imgListDiv = e.parentNode.querySelector('.feed_imgList')
  let first_img = feed_imgListDiv.firstChild.nextSibling
  while (first_img.nodeType !== 1) {
    first_img = first_img.nextSibling
  }
  let last_img = feed_imgListDiv.lastChild.previousSibling
  while (last_img.nodeType !== 1) {
    last_img = last_img.previousSibling
  }
  last_img.after(first_img)
}

function feedFavorite() {
  console.log('연결')
}
// const feed_containerEle = document.querySelector('.feed_container')

// document.querySelector('#feed').addEventListener('scroll', () => {
//   let scrollLocation = document.documentElement.scrollTop  // 현재 스크롤바 위치
// 	let windowHeight = window.innerHeight                    // 스크린 창
// 	let fullHeight = document.body.scrollHeight              //  margin 값은 포함 x

// 	if(scrollLocation + windowHeight >= fullHeight){
// 		let feed_titleEle = document.createElement('div')
//     feed_titleEle.className = 'feed_title'
//     feed_containerEle.appendChild(feed_titleEle)
//     let imgEle = document.createElement('img')
//     imgEle.src = "/resources/img/common/basic_profile.png"
//     imgEle.alt = "기본프로필사진"
//     feed_titleEle.appendChild(img)
//     let spanEle = document.createElement('span')
//     spanEle.innerText = 'USER234'
//     spanEle.innerText = '2021-01-23'
//     feed_titleEle.appendChild(spanEle)
//     let iEle = document.createElement('i')
//     iEle.className = 'fas fa-ellipsis-h'
//     feed_titleEle.appendChild(iEle)

//     feed_titleEle.className = 'feed_img'
//     feed_titleEle.onload = "loadImg()"
//     feed_containerEle.appendChild(feed_titleEle)
//     feed_titleEle.className = 'previous'
//     feed_titleEle.onclick = "previousImg(this)"

// 	}
// })