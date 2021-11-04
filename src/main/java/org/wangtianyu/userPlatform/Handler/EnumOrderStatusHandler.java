package org.wangtianyu.userPlatform.Handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.wangtianyu.userPlatform.Model.DonateOrder;
import org.wangtianyu.userPlatform.Model.DonateProject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumOrderStatusHandler extends BaseTypeHandler<DonateOrder.OrderStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DonateOrder.OrderStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter.name());
    }

    @Override
    public DonateOrder.OrderStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return DonateOrder.OrderStatus.valueOf(rs.getString(columnName));
    }

    @Override
    public DonateOrder.OrderStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return DonateOrder.OrderStatus.valueOf(rs.getString(columnIndex));
    }

    @Override
    public DonateOrder.OrderStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return DonateOrder.OrderStatus.valueOf(cs.getString(columnIndex));
    }
}
