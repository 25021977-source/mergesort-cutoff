# Thực nghiệm CUTOFF trong Mergesort

## Mục tiêu

Tìm ngưỡng CUTOFF phù hợp với chương trình mergesort trên máy dùng để đo. Khi đoạn mảng có số phần tử nhỏ hơn hoặc bằng CUTOFF, chương trình dùng insertion sort để sắp xếp đoạn đó.

## Thí nghiệm ban đầu

Thí nghiệm dùng Java 21.0.11 trên Windows và thử hai ngưỡng 7 và 10 với cùng một mảng ngẫu nhiên gồm 10.000 số nguyên trong tệp `data/random_10000.txt`. Trước mỗi lượt, chương trình sao chép mảng gốc và tạo mảng phụ. Chương trình dùng `System.nanoTime()` để đo từ lúc gọi hàm sắp xếp đến khi hàm kết thúc. Sau đó, chương trình đối chiếu kết quả với mảng đã được sắp xếp bằng `Arrays.sort`.

Chương trình chạy 10 vòng khởi động rồi ghi kết quả của 10 vòng tiếp theo. Mỗi vòng thử cả hai ngưỡng và đảo thứ tự thử so với vòng trước. Tệp [pilot.csv](results/pilot.csv) lưu 20 số đo theo đơn vị nano giây.

| CUTOFF | Số lượt ghi kết quả | Thời gian trung vị (ms) |
|---:|---:|---:|
| 7 | 10 | 0,54195 |
| 10 | 10 | 0,53115 |

## Nhận xét ban đầu

Trên mảng này, CUTOFF 10 có thời gian trung vị thấp hơn CUTOFF 7 là 0,01080 ms. Lấy CUTOFF 7 làm mốc, mức giảm là (0,54195 − 0,53115) / 0,54195 × 100 ≈ 1,99%. Ở lượt đo đầu CUTOFF 7 nhanh hơn, còn ở lượt thứ hai CUTOFF 10 nhanh hơn. Vì vậy, báo cáo dùng trung vị của cả 10 lượt để so sánh.

Kết quả trên mới áp dụng cho một tệp dữ liệu và hai ngưỡng đã thử. Bước tiếp theo là thử thêm các ngưỡng trên cả 9 tệp dữ liệu, lập bảng và vẽ đồ thị trước khi chọn CUTOFF cho chương trình.
