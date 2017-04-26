package de.regis24.business.datasource.manufacturer;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerDbFixturesHelper {

  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public ManufacturerDbFixturesHelper(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insertOrderType(Long typeId, String name) {
    jdbcTemplate.update(
        "INSERT IGNORE INTO ha_arten" +
            " (ha_arten_id, ha_arten_name)" +
            " VALUES (:order_type_id, :order_type_name)",
        ImmutableMap.<String, Object>builder()
            .put("order_type_id", typeId)
            .put("order_type_name", name)
            .build()
    );
  }

  public void deleteOrderType(Long typeId) {
    jdbcTemplate.update(
        "DELETE FROM ha_arten" +
            " WHERE ha_arten_id=:order_type_id",
        ImmutableMap.<String, Object>builder()
            .put("order_type_id", typeId)
            .build()
    );
  }

  /**
   * Inserts a row into "ha_art_definition".
   *
   * @param orderTypeId   Order type ID ("ha_art_id")
   * @param productTypeId Product type ID ("produkt_typ")
   */
  public void insertOrderTypePermit(Long orderTypeId, Long productTypeId) {
    jdbcTemplate.update(
        "INSERT IGNORE INTO ha_art_definition" +
            " (ha_art_id, produkt_typ)" +
            " VALUES (:order_type_id, :product_type_id)",
        ImmutableMap.<String, Object>builder()
            .put("order_type_id", orderTypeId)
            .put("product_type_id", productTypeId)
            .build()
    );
  }

  public void deleteOrderPermitsByOrder(Long orderTypeId) {
    jdbcTemplate.update(
        "DELETE FROM ha_art_definition WHERE ha_art_id=:order_type_id",
        ImmutableMap.<String, Object>builder()
            .put("order_type_id", orderTypeId)
            .build()
    );
  }

  public void deleteOrderPermitsByProduct(Long productType) {
    jdbcTemplate.update(
        "DELETE FROM ha_art_definition WHERE produkt_typ=:product_type_id",
        ImmutableMap.<String, Object>builder()
            .put("product_type_id", productType)
            .build()
    );
  }

  /**
   * Inserts a row into "manufacturer_ha".
   *
   * @param manufacturerId Manufacturer ID ("ma_id")
   * @param orderTypeId    Order type ID ("ha_art_id")
   */
  public void insertOrderTypeAssignment(Long manufacturerId, Long orderTypeId) {
    jdbcTemplate.update(
        "INSERT IGNORE INTO manufacturer_ha" +
            " (ma_id, ha_art_id, last_modified, bank_account_id, kto_inhaber)" +
            " VALUES (:manufacturer_id, :order_type_id, CURRENT_TIMESTAMP(), :bank_acc_id, " +
            ":kto_inhaber)",
        ImmutableMap.<String, Object>builder()
            .put("manufacturer_id", manufacturerId)
            .put("order_type_id", orderTypeId)
            .put("bank_acc_id", OrderTypesService.DEFAULT_BANK_ACCOUNT_ID)
            .put("kto_inhaber", 0)
            .build()
    );
  }

  public void deleteOrderTypeAssignments(Long manufacturerId) {
    jdbcTemplate.update(
        "DELETE FROM manufacturer_ha WHERE ma_id=:manufacturer_id",
        ImmutableMap.<String, Object>builder()
            .put("manufacturer_id", manufacturerId)
            .build()
    );
  }

  public void insertProductAssignment(long manufacturerId, long productType) {
    jdbcTemplate.update(
        "INSERT IGNORE INTO products (manufacturers_id, produkt_typ, tmp_produkt, " +
            "products_date_added, " +
            "products_tax_class_id) " +
            "VALUES(:man_id, :product_type, :is_product_temp, :date_added, :tax_class_id)",
        ImmutableMap.<String, Object>builder()
            .put("man_id", manufacturerId)
            .put("product_type", productType)
            .put("is_product_temp", 1)
            .put("date_added", new Date())
            .put("tax_class_id", 0)
            .build()
    );
  }

  public void deleteProductAssignments(long manufacturerId) {
    jdbcTemplate.update(
        "DELETE FROM products WHERE manufacturers_id=:man_id",
        ImmutableMap.<String, Object>builder()
            .put("man_id", manufacturerId)
            .build()
    );
  }

  public void insertProductType(Long typeId, String name) {
    jdbcTemplate.update(
        "INSERT IGNORE INTO anfrage_arten" +
            " (anfrage_art, art_name, art_text_kurz)" +
            " VALUES (:type_id, :name, :text)",
        ImmutableMap.<String, Object>builder()
            .put("type_id", typeId)
            .put("name", name)
            .put("text", "")
            .build()
    );
  }

  public void deleteProductType(Long typeId) {
    jdbcTemplate.update(
        "DELETE FROM anfrage_arten" +
            " WHERE anfrage_art=:type_id",
        ImmutableMap.<String, Object>builder()
            .put("type_id", typeId)
            .build()
    );
  }

  public List<Long> findProductsForManufacturer(Long manufacturerId) {
    return jdbcTemplate
        .query("SELECT products_id, produkt_typ FROM products WHERE manufacturers_id = :ma_id",
            Collections.singletonMap("ma_id", manufacturerId),
            (rs, rowNum) -> rs.getLong("produkt_typ"));
  }

  public List<Long> findOrderTypes(Long manufacturerId) {
    return jdbcTemplate.query("SELECT ha_art_id FROM manufacturer_ha WHERE ma_id=:ma_id",
        Collections.singletonMap("ma_id", manufacturerId),
        (rs, rowNum) -> rs.getLong("ha_art_id"));
  }
}
