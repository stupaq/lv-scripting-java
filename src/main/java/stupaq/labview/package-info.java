/**
 * The package contains Java bindings for LabVIEW Scripting API. The actual VIs being called by
 * this code can be found in lv-scripting repository (included as a Git sub-module).
 *
 * <p>
 *   The following list is meant to be a non comprehensive mapping from parts of implemented
 *   functionality to actual implementing packages and classes.
 * </p>
 * <ul>
 *   <li>
 *     VI Server class hierarchy is partially implemented as a Java class hierarchy in
 *     {@link stupaq.labview.hierarchy} package.
 *   </li>
 *   <li>
 *     Each tool VI from lv-scripting repository has an appropriate wrapper in
 *     {@link stupaq.labview.scripting.activex} package.
 *   </li>
 *   <li>
 *     Each call to any VI is routed through the following method
 *     {@link stupaq.labview.activex.StdCallRunnableVI#stdCall(Object...)}.
 *   </li>
 *   <li>
 *     VI reading is implemented mainly in {@link stupaq.labview.parsing.ParsedVI} class,
 *     which feeds provided implementation of {@link stupaq.labview.parsing.VIElementsVisitor}
 *     interface with a stream of GObjects and Generics found in specified VI.
 *   </li>
 * </ul>
 */
package stupaq.labview;
