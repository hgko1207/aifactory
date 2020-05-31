<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/taglib.jsp"%>

<link href="${pageContext.request.contextPath}/resources/css/leaderboard.css" rel="stylesheet" type="text/css">

<div class="card">
	<div class="card-header bg-white pb-0 pt-sm-0 pr-sm-0 header-elements-inline">
		<div class="header-elements">
			<ul class="nav nav-tabs nav-tabs-bottom card-header-tabs mx-0">
				<li class="nav-item">
					<a href="#public-tab" class="nav-link active show" data-toggle="tab">
						<i class="icon-trophy3 mr-2"></i>Public Leaderboard
					</a>
				</li>
				<li class="nav-item">
					<a href="#private-tab" class="nav-link" data-toggle="tab">
						<i class="icon-trophy2 mr-2"></i>private Leaderboard
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="tab-content">
		<div class="tab-pane fade active show" id="public-tab">
            <div class="card-footer d-flex justify-content-between align-items-center">
				<div class="text-muted font-size-14">
					<div>이 점수 현황은 테스트 데이터의 약 50% 로 계산됩니다.</div>
				</div>
                <button type="button" class="btn bg-blue">Submission</button>
			</div>
			<table class="table table-hover" id="leaderboardTable"> 
				<colgroup>
					<col width="5%"> 
					<col width="10%"> 
					<col width="10%">
					<col width="8%">
					<col width="10%"> 
					<col width="8%"> 
					<col width="*"> 
					<col width="10%"> 
				</colgroup>
				<thead>
					<tr class="competition-table__header-row text-center">
						<th class="text-right">No.</th>
						<th class="text-left pl-5">UserName</th>
						<th class="text-left pl-5">TeamName</th>
						<th>Score</th>
						<th>Last</th>
						<th>lap</th>
						<th>CheckPoint</th>
						<th></th>
					</tr>
				</thead>
				<tbody class="text-center">
					<tr class="competition-table__row text-center">
						<td class="text-right">1</td>
						<td class="font-weight-semibold text-left pl-5">hsjang</td>
						<td>ispace</td>
						<td>90</td>
						<td>1h</td>
						<td>1/5</td>
						<td>10/20</td>
						<td>00:03:15</td>
					</tr>
				</tbody>
			</table>
		</div>
	
		<div class="tab-pane fade" id="private-tab">
			<div class="card-footer d-flex justify-content-between">
				<div class="text-muted font-size-14">
					<div>최종 결과는 나머지 50% 를 기준으로 하므로 최종 순위가 다를 수 있습니다.</div>
				</div>
			</div>
		</div>
	</div>
</div>