package core.pickupbackend.device.infra.repository;

import core.pickupbackend.device.application.out.DeviceRepository;
import core.pickupbackend.device.domain.Device;
import core.pickupbackend.device.domain.mapper.DeviceMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcDeviceRepository implements DeviceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Device> rowMapper = new DeviceMapper();

    public JdbcDeviceRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Device save(final Device device) {
        String sql = "INSERT INTO pickup_db.device (member_id, fcm_token, device_type, last_login_at) "
                + "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, device.getMemberId() != null ? device.getMemberId() : 0);
            ps.setString(2, device.getFcmToken());
            ps.setString(3, String.valueOf(device.getDeviceType()));
            ps.setTimestamp(4, device.getLastLoginAt() != null
                    ? Timestamp.valueOf(device.getLastLoginAt())
                    : null);
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        return findById(generatedId).orElseThrow();
    }

    @Override
    public Optional<Device> findById(final Long id) {
        String sql = "SELECT * FROM pickup_db.device WHERE id = ?";
        List<Device> results = jdbcTemplate.query(sql, rowMapper, id);

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<Device> findByFcmToken(final String deviceToken) {
        String sql = "SELECT * FROM pickup_db.device WHERE fcm_token = ?";
        List<Device> results = jdbcTemplate.query(sql, rowMapper, deviceToken);

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Device> findAll() {
        String sql = "SELECT * FROM pickup_db.device";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "DELETE FROM pickup_db.device WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Device> findByMemberId(final Long memberId) {
        String sql = "SELECT * FROM pickup_db.device WHERE member_id = ?";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    @Override
    public void deleteByFcmToken(final String fcmToken) {
        String sql = "DELETE FROM pickup_db.device WHERE fcm_token = ?";
        jdbcTemplate.update(sql, fcmToken);
    }

    @Override
    public void updateStatus(final Long deviceId, final boolean status) {
        String sql = "UPDATE pickup_db.device SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, deviceId);
    }
}
