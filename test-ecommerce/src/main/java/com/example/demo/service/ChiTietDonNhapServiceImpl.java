package com.example.demo.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.Constants;
import com.example.demo.model.ChiTietDonNhap;
import com.example.demo.model.DonNhap;
import com.example.demo.model.LoThuoc;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.request.ChiTietDonNhapRequest;
import com.example.demo.payload.response.ResponseObj;
import com.example.demo.repository.ChiTietDonNhapRepository;
import com.example.demo.repository.DonNhapRepository;
import com.example.demo.repository.LoThuocRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolationException;

@Service
public class ChiTietDonNhapServiceImpl implements ChiTietDonNhapService {

	@Autowired
	private ChiTietDonNhapRepository chiTietDonNhapRepository;
	
	@Autowired
	private LoThuocRepository loThuocRepository;
	
	@Autowired
	private DonNhapRepository donNhapRepository;
	
	@Autowired
	private LoThuocService loThuocService;


	public ChiTietDonNhapServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResponseObj getByID(String id) {
		try {
			long idLong = Long.parseLong(id);
			Optional<ChiTietDonNhap> optional = chiTietDonNhapRepository.findById(idLong);
			if (optional.isPresent()) {
				return new ResponseObj(Constants.SUCCESSCODE, Constants.OK, optional.get());
			} else {
				return new ResponseObj(Constants.SUCCESSCODE, Constants.NOTFOUND, null);

			}
		} catch (Exception e) {
			return new ResponseObj(Constants.ERRORCODE, Constants.ERROR, e.getMessage());
		}
	}

	@Override
	public ResponseObj search(BaseRequest baseRequest) {
		// TODO Auto-generated method stub

		try {
			Object customize = baseRequest.getCustomize();
			ObjectMapper mapper = new ObjectMapper();

			ChiTietDonNhapRequest chiTietDonNhap = mapper.convertValue(customize, new TypeReference<ChiTietDonNhapRequest>() {
			});

			if (baseRequest.getPageNumber() == null || baseRequest.getPageNumber() == 0) {
				baseRequest.setPageNumber(1);
			}
			if (baseRequest.getPageSize() == null || baseRequest.getPageSize() == 0) {
				baseRequest.setPageNumber(10);
			}

			Pageable pageable = PageRequest.of(baseRequest.getPageNumber() - 1, baseRequest.getPageSize(),
					Sort.by(Order.desc("id")));

			Specification<ChiTietDonNhap> specification = new Specification<ChiTietDonNhap>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<ChiTietDonNhap> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {

					List<Predicate> predicates = new ArrayList<>();

					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

				}
			};
			Page<ChiTietDonNhap> page = this.chiTietDonNhapRepository.findAll(specification, pageable);
			return new ResponseObj(Constants.SUCCESSCODE, Constants.OK, page);
		} catch (Exception e) {
			return new ResponseObj(Constants.ERRORCODE, Constants.ERROR, e.getMessage());
		}
	}

	@Override
	@Transactional
	public ResponseObj create(List<ChiTietDonNhapRequest> chiTietDonNhapRequests) {
		try {
			List<ChiTietDonNhap> chiTietDonNhaps = new ArrayList<ChiTietDonNhap>();
			for(ChiTietDonNhapRequest item : chiTietDonNhapRequests) {
				DonNhap donNhap = new DonNhap();
				LoThuoc loThuoc = new LoThuoc();
				Optional<DonNhap> dn = this.donNhapRepository.findById(item.getDonNhap());
				Optional<LoThuoc> lt = this.loThuocRepository.findById(item.getLoThuoc());
				
				if(dn.isPresent()) {
					donNhap = dn.get();
				} else {
					return new ResponseObj(Constants.ERRORCODE, Constants.THEMMOITHATBAI, "Mã đơn nhập không tông tại: " + item.getDonNhap().toString());
				}
				if(lt.isPresent()) {
					loThuoc = lt.get();
				} else {
					return new ResponseObj(Constants.ERRORCODE, Constants.THEMMOITHATBAI, "Mã lô thuốc không tồn tại: " + item.getLoThuoc().toString());
				}
				
				ChiTietDonNhap ctdn = new ChiTietDonNhap(item.getGiaNhap(), item.getGiaBan(),
						item.getSoLuong(), item.getHsd(), item.getNsx(), donNhap, loThuoc);
				chiTietDonNhaps.add(ctdn);
			}
			
			List<ChiTietDonNhap> result = this.chiTietDonNhapRepository.saveAll(chiTietDonNhaps);
			BigDecimal total = BigDecimal.ZERO;
			for(ChiTietDonNhap item : result) {
				LoThuoc loThuoc = item.getLoThuoc();
				loThuoc.setGiaBan(item.getGiaBan());
				loThuoc.setGiaNhap(item.getGiaNhap());
				loThuoc.setNSX(item.getNsx());
				loThuoc.setHSD(item.getHsd());
				loThuoc.setSoLuong(loThuoc.getSoLuong() + item.getSoLuong());
				this.loThuocService.update(loThuoc);
				total = total.add(item.getGiaNhap().multiply(BigDecimal.valueOf(item.getSoLuong())));
			}
			ChiTietDonNhap ctdnup = result.get(0);
			Long iddn = ctdnup.getDonNhap().getId();
			Optional<DonNhap> dnop = this.donNhapRepository.findById(iddn);
			DonNhap dnfn = dnop.get();
			dnfn.setTongTien(total);
			this.donNhapRepository.save(dnfn);
			
			return new ResponseObj(Constants.SUCCESSCODE, Constants.THEMMOITHANHCONG, result);
		} catch (Exception e) {
			String errorMessage = "Lỗi chưa xác định!";
			if (e instanceof DataIntegrityViolationException) {
				Throwable cause = e.getCause();
				while ((cause != null) && !(cause instanceof SQLException)) {
					cause = cause.getCause();
				}
				if (cause instanceof SQLException) {
					int errorCode = ((SQLException) cause).getErrorCode();
					switch (errorCode) {
					case 1062:
						errorMessage = "Lỗi trùng khóa: " + cause.getMessage();
						break;
					case 1452:
						errorMessage = "Lỗi khóa ngoại: Không tìm thấy khóa ngoại tương ứng: " + cause.getMessage();
						break;
					default:
						errorMessage = "Lỗi SQL: " + cause.getMessage();
						break;
					}
				}
			} else if (e instanceof TransactionSystemException) {
				Throwable cause = e.getCause();
				if (cause instanceof RollbackException) {
					Throwable rootCause = cause.getCause();
					if (rootCause instanceof ConstraintViolationException) {
						errorMessage = "Lỗi ràng buộc: " + rootCause.getMessage();
					}
				}
			} else if (e instanceof OptimisticLockException) {
				errorMessage = "Lỗi đồng bộ hóa: Bản ghi đã bị thay đổi bởi người dùng khác!";
			} else {
				errorMessage = "Lỗi không xác định: " + e.getMessage();
			}
			return new ResponseObj(Constants.ERRORCODE, Constants.THEMMOITHATBAI, errorMessage);
		}

	}

	@Override
	public ResponseObj update(ChiTietDonNhap chiTietDonNhap) {
		try {
			Long id = chiTietDonNhap.getId();
			if (id != null) {

				Optional<ChiTietDonNhap> optional = this.chiTietDonNhapRepository.findById(id);
				if (optional.isPresent()) {
					this.chiTietDonNhapRepository.save(chiTietDonNhap);
					return new ResponseObj(Constants.SUCCESSCODE, Constants.CAPNHATTHANHCONG);
				} else {
					return new ResponseObj(Constants.ERRORCODE, Constants.IDNOTFOUND);
				}
			} else {
				return new ResponseObj(Constants.ERRORCODE, Constants.NEDUPDATE);
			}
		} catch (Exception e) {
			String errorMessage = "Lỗi chưa xác định!";
			if (e instanceof DataIntegrityViolationException) {
				Throwable cause = e.getCause();
				while ((cause != null) && !(cause instanceof SQLException)) {
					cause = cause.getCause();
				}
				if (cause instanceof SQLException) {
					int errorCode = ((SQLException) cause).getErrorCode();
					switch (errorCode) {
					case 1062:
						errorMessage = "Lỗi trùng khóa: Mã sản phẩm đã tồn tại: " + cause.getMessage();
						break;
					case 1452:
						errorMessage = "Lỗi khóa ngoại: Không tìm thấy khóa ngoại tương ứng: " + cause.getMessage();
						break;
					default:
						errorMessage = "Lỗi SQL: " + cause.getMessage();
						break;
					}
				}
			} else if (e instanceof TransactionSystemException) {
				Throwable cause = e.getCause();
				if (cause instanceof RollbackException) {
					Throwable rootCause = cause.getCause();
					if (rootCause instanceof ConstraintViolationException) {
						errorMessage = "Lỗi ràng buộc: " + rootCause.getMessage();
					}
				}
			} else if (e instanceof OptimisticLockException) {
				errorMessage = "Lỗi đồng bộ hóa: Bản ghi đã bị thay đổi bởi người dùng khác!";
			} else {
				errorMessage = "Lỗi không xác định: " + e.getMessage();
			}
			return new ResponseObj(Constants.ERRORCODE, Constants.CAPNHATTHATBAI, errorMessage);
		}
	}

	@Override
	public ResponseObj delete(String id) {
		try {
			long idLong = Long.parseLong(id);
			this.chiTietDonNhapRepository.deleteById(idLong);
			return new ResponseObj(Constants.SUCCESSCODE, Constants.XOATHANHCONG);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseObj(Constants.ERROR, Constants.XOATHATBAI, "Không tìm thấy dữ liệu với id = " + id);
		} catch (DataIntegrityViolationException e) {
			return new ResponseObj(Constants.ERROR, Constants.XOATHATBAI,
					"Không thể xóa dữ liệu này do có liên kết với bảng khác: " + e.getMessage());
		} catch (Exception e) {
			return new ResponseObj(Constants.ERROR, Constants.XOATHATBAI, "Lỗi không xác định: " + e.getMessage());
		}
	}

}
