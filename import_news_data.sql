-- --------------------------------------------------------
-- Script to import sample data for News and NewsCategory
-- Database: tour_db
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

USE `tour_db`;

-- --------------------------------------------------------
-- Clear existing data (optional - comment out if you want to keep existing data)
-- --------------------------------------------------------
-- DELETE FROM `news` WHERE id > 0;
-- DELETE FROM `news_category` WHERE id > 0;
-- ALTER TABLE `news` AUTO_INCREMENT = 1;
-- ALTER TABLE `news_category` AUTO_INCREMENT = 1;

-- --------------------------------------------------------
-- Insert data for table `news_category`
-- --------------------------------------------------------

INSERT INTO `news_category` (`name`, `description`, `slug`, `active`, `created_at`, `updated_at`) VALUES
('Du lịch trong nước', 'Tin tức về các điểm du lịch trong nước, kinh nghiệm du lịch Việt Nam', 'du-lich-trong-nuoc', 1, NOW(), NOW()),
('Du lịch quốc tế', 'Tin tức về du lịch nước ngoài, các điểm đến hấp dẫn trên thế giới', 'du-lich-quoc-te', 1, NOW(), NOW()),
('Khuyến mãi', 'Các chương trình khuyến mãi, ưu đãi tour du lịch', 'khuyen-mai', 1, NOW(), NOW()),
('Kinh nghiệm du lịch', 'Chia sẻ kinh nghiệm, mẹo vặt khi đi du lịch', 'kinh-nghiem-du-lich', 1, NOW(), NOW()),
('Ẩm thực', 'Tin tức về ẩm thực địa phương, đặc sản các vùng miền', 'am-thuc', 1, NOW(), NOW()),
('Văn hóa', 'Tin tức về văn hóa, lễ hội, phong tục tập quán', 'van-hoa', 1, NOW(), NOW());

-- --------------------------------------------------------
-- Insert data for table `news`
-- --------------------------------------------------------

INSERT INTO `news` (`title`, `content`, `summary`, `image`, `slug`, `category_id`, `author`, `views`, `active`, `featured`, `created_at`, `updated_at`) VALUES
('Khám phá Đà Lạt - Thành phố ngàn hoa', 
'Đà Lạt là một trong những điểm du lịch hấp dẫn nhất Việt Nam với khí hậu mát mẻ quanh năm, cảnh quan thiên nhiên tuyệt đẹp và nhiều địa điểm tham quan nổi tiếng. Thành phố này thu hút hàng triệu du khách mỗi năm với những vườn hoa rực rỡ, hồ nước thơ mộng và kiến trúc Pháp cổ kính.

Du khách có thể tham quan các địa điểm nổi tiếng như Hồ Xuân Hương, Thung lũng Tình Yêu, Dinh Bảo Đại, hay thưởng thức các món đặc sản địa phương như dâu tây, atiso, và các loại rau củ tươi ngon.',
'Đà Lạt - điểm đến lý tưởng cho những ai yêu thích thiên nhiên và muốn trốn khỏi cái nóng của thành phố.',
'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800',
'kham-pha-da-lat-thanh-pho-ngan-hoa',
1,
'Admin',
0,
1,
1,
NOW(),
NOW()),

('Hạ Long - Kỳ quan thiên nhiên thế giới', 
'Vịnh Hạ Long là một trong những kỳ quan thiên nhiên nổi tiếng nhất thế giới, được UNESCO công nhận là Di sản Thiên nhiên Thế giới. Với hàng nghìn đảo đá vôi kỳ vĩ nhô lên từ mặt nước xanh ngọc, Hạ Long tạo nên một khung cảnh ngoạn mục.

Du khách có thể tham gia các tour du thuyền, khám phá các hang động kỳ bí như Hang Sửng Sốt, Hang Đầu Gỗ, hoặc tham gia các hoạt động như chèo kayak, bơi lội, và thưởng thức hải sản tươi ngon.',
'Vịnh Hạ Long - điểm đến không thể bỏ qua khi đến Việt Nam với vẻ đẹp thiên nhiên độc đáo.',
'https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=800',
'ha-long-ky-quan-thien-nhien-the-gioi',
1,
'Admin',
0,
1,
1,
NOW(),
NOW()),

('Bangkok - Thủ đô của Thái Lan', 
'Bangkok là một thành phố sôi động với sự kết hợp hài hòa giữa văn hóa truyền thống và hiện đại. Thành phố này nổi tiếng với các ngôi chùa vàng lộng lẫy, ẩm thực đường phố phong phú, và cuộc sống về đêm sôi động.

Du khách có thể tham quan Cung điện Hoàng gia, Chùa Phật Vàng, hoặc đi mua sắm tại các chợ nổi và trung tâm thương mại lớn. Ẩm thực Thái Lan với các món như Pad Thai, Tom Yum, và Mango Sticky Rice là những trải nghiệm không thể bỏ qua.',
'Bangkok - điểm đến lý tưởng cho những ai yêu thích văn hóa, ẩm thực và mua sắm.',
'https://images.unsplash.com/photo-1552465011-b4e21bf6e79a?w=800',
'bangkok-thu-do-cua-thai-lan',
2,
'Admin',
0,
1,
1,
NOW(),
NOW()),

('Tokyo - Thành phố tương lai', 
'Tokyo là một trong những thành phố hiện đại và sôi động nhất thế giới, nơi hội tụ giữa truyền thống và công nghệ tiên tiến. Thành phố này mang đến những trải nghiệm độc đáo từ các ngôi đền cổ kính đến các tòa nhà chọc trời, từ ẩm thực truyền thống đến các món ăn hiện đại.

Du khách có thể tham quan Tháp Tokyo, Đền Senso-ji, hoặc khám phá các khu phố sầm uất như Shibuya, Harajuku. Ẩm thực Nhật Bản với sushi, ramen, và wagyu là những món ăn không thể bỏ qua.',
'Tokyo - thành phố của tương lai với sự kết hợp hoàn hảo giữa truyền thống và hiện đại.',
'https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=800',
'tokyo-thanh-pho-tuong-lai',
2,
'Admin',
0,
1,
0,
NOW(),
NOW()),

('Ưu đãi đặc biệt: Giảm 30% tour Đà Lạt 3 ngày 2 đêm', 
'Chào mừng mùa du lịch mới, chúng tôi xin gửi đến quý khách chương trình khuyến mãi đặc biệt: Giảm 30% cho tour Đà Lạt 3 ngày 2 đêm.

Tour bao gồm:
- Khách sạn 3 sao tại trung tâm Đà Lạt
- Xe đưa đón sân bay
- Hướng dẫn viên chuyên nghiệp
- Bữa sáng buffet tại khách sạn
- Tham quan các điểm nổi tiếng: Hồ Xuân Hương, Thung lũng Tình Yêu, Dinh Bảo Đại

Thời gian áp dụng: Từ ngày 01/01/2025 đến 31/03/2025
Số lượng có hạn, đặt ngay để nhận ưu đãi!',
'Chương trình khuyến mãi đặc biệt giảm 30% tour Đà Lạt 3 ngày 2 đêm. Đặt ngay để nhận ưu đãi!',
'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800',
'uu-dai-dac-biet-giam-30-tour-da-lat-3-ngay-2-dem',
3,
'Admin',
0,
1,
1,
NOW(),
NOW()),

('Mẹo vặt khi đi du lịch: Những điều cần biết', 
'Để có một chuyến du lịch suôn sẻ và an toàn, bạn cần chuẩn bị kỹ lưỡng và nắm vững một số mẹo vặt hữu ích:

1. Chuẩn bị hành lý: Chỉ mang những gì thực sự cần thiết, tránh mang quá nhiều đồ
2. Bảo hiểm du lịch: Luôn mua bảo hiểm du lịch để được bảo vệ trong trường hợp khẩn cấp
3. Nghiên cứu trước: Tìm hiểu về điểm đến, văn hóa địa phương, và các quy định
4. Giữ liên lạc: Luôn thông báo cho người thân về lịch trình và địa điểm
5. Tiền mặt và thẻ: Mang cả tiền mặt và thẻ tín dụng để dự phòng
6. Ứng dụng hữu ích: Tải các ứng dụng bản đồ, dịch thuật, và đặt phòng

Những mẹo nhỏ này sẽ giúp bạn có một chuyến du lịch an toàn và thú vị hơn.',
'Những mẹo vặt hữu ích giúp bạn có một chuyến du lịch suôn sẻ và an toàn.',
'https://images.unsplash.com/photo-1488646953014-85cb44e25828?w=800',
'meo-vat-khi-di-du-lich-nhung-dieu-can-biet',
4,
'Admin',
0,
1,
0,
NOW(),
NOW()),

('Phở - Món ăn biểu tượng của Việt Nam', 
'Phở là món ăn truyền thống nổi tiếng nhất của Việt Nam, được yêu thích không chỉ trong nước mà còn trên toàn thế giới. Món ăn này có nguồn gốc từ miền Bắc Việt Nam và đã trở thành biểu tượng ẩm thực của đất nước.

Phở được làm từ bánh phở mềm, nước dùng trong và thơm, cùng với thịt bò hoặc thịt gà. Món ăn được ăn kèm với các loại rau thơm như hành lá, ngò gai, và chanh. Mỗi vùng miền có cách nấu phở riêng, tạo nên sự đa dạng và phong phú cho món ăn này.

Phở không chỉ là một món ăn mà còn là một phần của văn hóa Việt Nam, thể hiện sự tinh tế và khéo léo trong ẩm thực.',
'Phở - món ăn biểu tượng của Việt Nam được yêu thích trên toàn thế giới.',
'https://images.unsplash.com/photo-1559339352-11d035aa65de?w=800',
'pho-mon-an-bieu-tuong-cua-viet-nam',
5,
'Admin',
0,
1,
0,
NOW(),
NOW()),

('Lễ hội Hoa Anh Đào tại Nhật Bản', 
'Lễ hội Hoa Anh Đào (Hanami) là một trong những lễ hội quan trọng nhất của Nhật Bản, diễn ra vào mùa xuân khi hoa anh đào nở rộ. Đây là dịp để người dân và du khách tụ tập dưới những cây anh đào để ngắm hoa, thưởng thức ẩm thực và tận hưởng không khí lễ hội.

Lễ hội này không chỉ là một sự kiện văn hóa mà còn là biểu tượng của sự đẹp đẽ và mong manh của cuộc sống. Người Nhật coi hoa anh đào như một biểu tượng của sự tái sinh và hy vọng.

Các địa điểm nổi tiếng để ngắm hoa anh đào bao gồm Công viên Ueno ở Tokyo, Núi Yoshino ở Nara, và Lâu đài Himeji. Du khách có thể tham gia các hoạt động như picnic dưới cây anh đào, tham gia các lễ hội địa phương, và thưởng thức các món ăn đặc biệt của mùa xuân.',
'Lễ hội Hoa Anh Đào - một trong những lễ hội văn hóa quan trọng nhất của Nhật Bản.',
'https://images.unsplash.com/photo-1522383225653-ed111181a951?w=800',
'le-hoi-hoa-anh-dao-tai-nhat-ban',
6,
'Admin',
0,
1,
0,
NOW(),
NOW()),

('Sapa - Vẻ đẹp núi rừng Tây Bắc', 
'Sapa là một thị trấn nghỉ dưỡng nổi tiếng ở vùng Tây Bắc Việt Nam, nằm ở độ cao 1.600m so với mực nước biển. Nơi đây nổi tiếng với cảnh quan núi non hùng vĩ, ruộng bậc thang xanh mướt, và văn hóa đa dạng của các dân tộc thiểu số.

Du khách có thể tham quan Núi Fansipan - nóc nhà Đông Dương, tham gia trekking qua các bản làng dân tộc, hoặc thưởng thức các món ăn địa phương đặc sắc. Khí hậu mát mẻ quanh năm và cảnh quan thiên nhiên tuyệt đẹp khiến Sapa trở thành điểm đến lý tưởng cho những ai yêu thích thiên nhiên và muốn trải nghiệm văn hóa địa phương.',
'Sapa - điểm đến lý tưởng để khám phá vẻ đẹp núi rừng Tây Bắc và văn hóa các dân tộc thiểu số.',
'https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=800',
'sapa-ve-dep-nui-rung-tay-bac',
1,
'Admin',
0,
1,
0,
NOW(),
NOW()),

('Singapore - Đảo quốc sư tử', 
'Singapore là một quốc đảo nhỏ nhưng phát triển mạnh mẽ, nổi tiếng với sự sạch sẽ, hiện đại và đa văn hóa. Thành phố này là sự kết hợp hoàn hảo giữa văn hóa châu Á và phương Tây, tạo nên một điểm đến độc đáo và hấp dẫn.

Du khách có thể tham quan Gardens by the Bay, Marina Bay Sands, hoặc thưởng thức ẩm thực đa dạng tại các khu phố ẩm thực. Singapore cũng nổi tiếng với shopping, với nhiều trung tâm thương mại lớn và các cửa hàng miễn thuế.',
'Singapore - đảo quốc sư tử với sự kết hợp hoàn hảo giữa văn hóa và hiện đại.',
'https://images.unsplash.com/photo-1525625293386-24f372d2b5e3?w=800',
'singapore-dao-quoc-su-tu',
2,
'Admin',
0,
1,
0,
NOW(),
NOW());

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

