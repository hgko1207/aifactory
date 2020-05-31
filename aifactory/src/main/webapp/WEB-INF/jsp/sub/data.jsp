<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>

<div class="card">
	<div class="card-header bg-white header-elements-inline">
		<h5 class="card-title">
			<i class="icon-database4 mr-2"></i>데이터 셋 설명
		</h5>
		<div class="header-elements">
			<button type="button" class="btn bg-blue" data-toggle="modal" data-target="#addDescriptionModal">Edit</button>
		</div>
	</div>
	<div class="card-body">
		<div id="data-content" class="markdown-converter__text--rendered">
                    이 훈련 자료에는 혹등고래 떼의 수천 개의 모습이 담겨 있습니다. 각각의 고래는 연구원들에 의해 확인되었고 Id를 부여받았습니다. 과제는 테스트셋에 있는 이미지의 고래 Id를 예측하는 것입니다. 이 대회의 목적은 몇 개의 사진으로부터 3000종류 이상의 돌고래 Id를 예측하는것입니다.
        </div>
	</div>
</div>
<div class="card">
    <div class="card-header bg-white header-elements-inline">
        <h5 class="card-title">
            <i class="icon-database4 mr-2"></i>파일 설명
        </h5>
    </div>
    <div class="card-body">
        <div id="data-content" class="markdown-converter__text--rendered">
                    ＊ train.zip - 교육 이미지가 포함된 폴더입니다.</br>
                    ＊ train.csv - 훈련 Image를 적절한 고래 Id에 매핑합니다. 교육 데이터에서 라벨이 식별되지 않을 것으로 예상되는 고래는 new_whale로 라벨을 지정해야 합니다.</br>
                    ＊ test.zip - 고래 Id를 예측하기 위한 테스트 이미지가 포함된 폴더입니다.</br>
                    ＊ sample_submission.csv - 올바른 형식의 샘플 제출 파일입니다.
        </div>
    </div>
</div>
<div class="card">
	<div class="card-header bg-white header-elements-inline">
		<h5 class="card-title">
			<i class="icon-database4 mr-2"></i>데이터 셋
		</h5>
			<div class="header-elements">
				<button type="button" class="btn bg-blue" data-toggle="modal" data-target="#addDataFileModal">Upload Data</button>
				<button type="button" class="btn bg-blue ml-3" data-toggle="modal" data-target="#addDataFileModal">Download All</button>
			</div>
	</div>
	<div class="card-body">
        <div class="table-responsive">
            <table class="table">
                <colgroup>
                    <col width="*" />
                    <col width="10%"/>
                </colgroup>
                <thead>
                    <tr>
                        <th>게시판명</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody id="board_table">
                    <tr>
                        <td>
                            train.zip
                        </td>
                        <td>
                            <i class="icon-trash"></i>
                        </td>
                    </tr>
                     <tr>
                        <td>
                            train.csv
                        </td>
                        <td>
                            <i class="icon-trash"></i>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            test.zip
                        </td>
                        <td>
                            <i class="icon-trash"></i>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            sample_submission.csv
                        </td>
                        <td>
                            <i class="icon-trash"></i>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
	</div>
</div>
<script>
</script>