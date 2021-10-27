package org.wangtianyu.userPlatform.Handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.wangtianyu.userPlatform.Model.DonateProject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumProjectStatusHandler extends BaseTypeHandler<DonateProject.EnumProjectStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DonateProject.EnumProjectStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter.name());
    }

    @Override
    public DonateProject.EnumProjectStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return DonateProject.EnumProjectStatus.valueOf(rs.getString(columnName));
    }

    @Override
    public DonateProject.EnumProjectStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return DonateProject.EnumProjectStatus.valueOf(rs.getString(columnIndex));
    }

    @Override
    public DonateProject.EnumProjectStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return DonateProject.EnumProjectStatus.valueOf(cs.getString(columnIndex));
    }
}
