package com.example.demo.service;

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

import com.example.demo.common.Constants;
import com.example.demo.common.Utils;
import com.example.demo.model.DonXuat;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.response.ResponseObj;
import com.example.demo.repository.DonXuatRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolationException;

@Service
public class DonXuatServiceImpl implements DonXuatService{

	@Autowired
	private DonXuatRepository donXuatRepository;
	public DonXuatServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResponseObj getByID(String id) {
		try {
			long idLong = Long.parseLong(id);
			Optional<DonXuat> optional = donXuatRepository.findById(idLong);
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
		try {
			Object customize = baseRequest.getCustomize();
			ObjectMapper mapper = new ObjectMapper();
			DonXuat request = mapper.convertValue(customize, new TypeReference<DonXuat>() {
			});
			if (baseRequest.getPageNumber() == null || baseRequest.getPageNumber() == 0) {
				baseRequest.setPageNumber(1);
			}
			if (baseRequest.getPageSize() == null || baseRequest.getPageSize() == 0) {
				baseRequest.setPageNumber(10);
			}

			Pageable pageable = PageRequest.of(baseRequest.getPageNumber() - 1,
					baseRequest.getPageSize(), Sort.by(Order.desc("id")));
			
			Specification<DonXuat> specification = new Specification<DonXuat>() {

				private static final long serialVersionUID = 1L;
				@Override
				public Predicate toPredicate(Root<DonXuat> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					
					List<Predicate> predicates = new ArrayList<>();
					
					if (Utils.CheckNullString(request.getMaDonXuat())) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("maThuoc")), Utils.likeText(request.getMaDonXuat()))));
					}
					
					if (Utils.CheckNullString(request.getMaDonXuat())) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("id")), request.getId())));
					}					

					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

				}
			};
			Page<DonXuat> page = this.donXuatRepository.findAll(specification, pageable);
			return new ResponseObj(Constants.SUCCESSCODE, Constants.OK, page);
		} catch (Exception e) {
			return new ResponseObj(Constants.ERRORCODE, Constants.ERROR, e.getMessage());
		}
	}

	@Override
	public ResponseObj create(DonXuat donXuat) {
		try {
			DonXuat rs = this.donXuatRepository.save(donXuat);
			return new ResponseObj(Constants.SUCCESSCODE, Constants.THEMMOITHANHCONG,rs);
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
	public ResponseObj update(DonXuat donXuat) {
		try {
			Long id = donXuat.getId();
			if(id != null)
			{
				
				Optional<DonXuat> optional = this.donXuatRepository.findById(id);
				if(optional.isPresent()) {
					this.donXuatRepository.save(donXuat);
					return new ResponseObj(Constants.SUCCESSCODE, Constants.CAPNHATTHANHCONG);
				}
				else {
					return new ResponseObj(Constants.ERRORCODE, Constants.IDNOTFOUND);
				}
				
			}
			else {
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
			this.donXuatRepository.deleteById(idLong);
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
